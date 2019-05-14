package net.chaplinskiy.coinmarketcapclient.v1.controller;

import net.chaplinskiy.coinmarketcapclient.model.Ticker;
import net.chaplinskiy.coinmarketcapclient.payload.QuoteRequest;
import net.chaplinskiy.coinmarketcapclient.service.QuoteService;
import net.chaplinskiy.coinmarketcapclient.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/v1/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public ResponseEntity<?> getQuotePrice(@RequestBody QuoteRequest quoteRequest) throws IOException {
        Ticker quotePrice = quoteService.getQuotePrice(quoteRequest);
        return new ResponseEntity<>(quotePrice, HttpStatus.OK);
    }


    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCoinTickerAndConvertCurrency(@RequestParam String coinToken, @RequestParam String convertCurrency,
            @RequestParam(value = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) throws IOException {

        List<Ticker> allCoin = quoteService.getAllCoinTickerAndConvertCurrency(coinToken, convertCurrency, page, size, sort);
        return new ResponseEntity<>(allCoin, HttpStatus.OK);
    }
}

