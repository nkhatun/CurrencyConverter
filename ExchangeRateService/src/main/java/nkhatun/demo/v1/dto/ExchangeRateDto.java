package nkhatun.demo.v1.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class ExchangeRateDto implements Serializable {
private String convertTo;
private String convertFrom;
private BigDecimal convertAmount;
private Long retrievalCount;
private Date date;

}
