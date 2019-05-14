package net.chaplinskiy.coinmarketcapclient.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class QuoteRequest {
    @NotBlank
    private String coinTicker;

    @NotBlank
    private String convertCurrency;

    @NotBlank
    private String date;
}
