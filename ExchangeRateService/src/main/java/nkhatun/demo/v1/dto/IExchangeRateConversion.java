package nkhatun.demo.v1.dto;

import java.math.BigDecimal;
import java.util.Currency;

public interface IExchangeRateConversion {
	public BigDecimal convertCurrencyPair(Currency generateFrom, Currency generateTo);
	public void updateUsage(Currency used);
	
}
