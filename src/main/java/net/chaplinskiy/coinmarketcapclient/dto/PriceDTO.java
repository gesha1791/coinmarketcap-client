package net.chaplinskiy.coinmarketcapclient.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceDTO {
    private BigDecimal price;
}
