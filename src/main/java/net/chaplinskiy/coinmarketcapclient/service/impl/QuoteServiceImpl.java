package net.chaplinskiy.coinmarketcapclient.service.impl;

import com.google.gson.Gson;
import net.chaplinskiy.coinmarketcapclient.dto.PriceDTO;
import net.chaplinskiy.coinmarketcapclient.exception.ResourceNotFoundException;
import net.chaplinskiy.coinmarketcapclient.model.Ticker;
import net.chaplinskiy.coinmarketcapclient.payload.QuoteRequest;
import net.chaplinskiy.coinmarketcapclient.repository.TickerRepository;
import net.chaplinskiy.coinmarketcapclient.service.QuoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@Service
public class QuoteServiceImpl implements QuoteService {

    private final TickerRepository tickerRepository;

    @Value("${user.apiKey}")
    private String apiKey;

    @Autowired
    public QuoteServiceImpl(TickerRepository tickerRepository) {
        this.tickerRepository = tickerRepository;
    }

    private static final String COINMARKETCAP_URI
            = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Ticker getQuotePrice(QuoteRequest quoteRequest) throws IOException {

        String uriComponentsBuilder = UriComponentsBuilder.fromUriString(COINMARKETCAP_URI)
                .queryParam("symbol", quoteRequest.getCoinTicker())
                .queryParam("convert", quoteRequest.getConvertCurrency())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        headers.set(HttpHeaders.ACCEPT, "application/json");

        Ticker byCoinTickerAndConvertCurrencyAndDate = tickerRepository.findByCoinTickerAndConvertCurrencyAndDate
                (quoteRequest.getCoinTicker(), quoteRequest.getConvertCurrency(), LocalDate.parse(quoteRequest.getDate()));

        if(byCoinTickerAndConvertCurrencyAndDate == null) {
            ResponseEntity<String> exchange = restTemplate.exchange
                    (uriComponentsBuilder, HttpMethod.GET, new HttpEntity<Void>(headers), String.class);

            String body = exchange.getBody();

            String price = refactorString(body);

            Gson g = new Gson();

            PriceDTO priceDTO = g.fromJson(price, PriceDTO.class);

            if(priceDTO.getPrice() == null){
                throw new ResourceNotFoundException(quoteRequest.getCoinTicker() +
                        "/" + quoteRequest.getConvertCurrency() + " not have price this " + quoteRequest.getDate());
            }


            Ticker build = Ticker.builder()
                    .coinTicker(quoteRequest.getCoinTicker())
                    .convertCurrency(quoteRequest.getConvertCurrency())
                    .date(LocalDate.parse(quoteRequest.getDate()))
                    .price(priceDTO.getPrice())
                    .build();

            byCoinTickerAndConvertCurrencyAndDate = build;

            tickerRepository.save(build);
        }
        return byCoinTickerAndConvertCurrencyAndDate;
    }

    @Override
    public List<Ticker> getAllCoinTickerAndConvertCurrency(String coinToken, String convertCurrency, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sort), "date");
           return tickerRepository.findAllByCoinTickerAndConvertCurrency(coinToken, convertCurrency, pageable);
    }


    private String refactorString(String body) {
        body = body.substring(body.lastIndexOf("{"));
        body = body.replaceAll("}", "");
        return body + "}";
    }
}
