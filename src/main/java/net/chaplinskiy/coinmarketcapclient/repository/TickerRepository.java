package net.chaplinskiy.coinmarketcapclient.repository;

import net.chaplinskiy.coinmarketcapclient.model.Ticker;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TickerRepository extends JpaRepository<Ticker, Long> {
    Ticker findByCoinTickerAndConvertCurrencyAndDate(String coinTicker, String convertCurrency, LocalDate date);
    List<Ticker> findAllByCoinTickerAndConvertCurrency(String coinToken, String convertCurrency, Pageable pageable);
}
