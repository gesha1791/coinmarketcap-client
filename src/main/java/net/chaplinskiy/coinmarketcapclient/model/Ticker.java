package net.chaplinskiy.coinmarketcapclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import net.chaplinskiy.coinmarketcapclient.util.BigDecimalConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Builder
public class Ticker {

    @Id
    @GeneratedValue
    @Column(name = "ticker_id")
    private Long id;

    @Column(name = "coin_Ticker")
    private String coinTicker;

    @Column(name = "convert_Currency")
    private String convertCurrency;

    @Column(name = "date")
    private LocalDate date;

    @Convert(converter = BigDecimalConverter.class)
    private BigDecimal price;
}
