package nkhatun.demo.v1.crawler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
public interface IExchangeRateCrawlerService {
	public Optional<HashMap<String, BigDecimal>> collectExchangeRates();

	public void saveExchangeRates();
}
