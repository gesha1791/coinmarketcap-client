package net.chaplinskiy.coinmarketcapclient.service;


import net.chaplinskiy.coinmarketcapclient.model.Ticker;
import net.chaplinskiy.coinmarketcapclient.payload.QuoteRequest;

import java.io.IOException;
import java.util.List;


public interface QuoteService {
    Ticker getQuotePrice(QuoteRequest quoteRequest) throws IOException;

    List<Ticker> getAllCoinTickerAndConvertCurrency(String coinToken, String convertCurrency, int page, int size, String sort);
}
