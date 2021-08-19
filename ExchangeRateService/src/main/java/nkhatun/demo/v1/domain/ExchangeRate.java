package nkhatun.demo.v1.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EXCHANGE_RATE")
public class ExchangeRate {
	@Id
	@Column(name = "EXCH_ID", nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long exchangeRateId;

	@Column(name = "CURRENCY_CODE", nullable = false)
	private String currencyCode;

	@Column(name = "DAILY_RATE", nullable = false)
	private String dailyRate;

	@Column(name = "FETCHED_ON", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fetchedOn;

	@Column(name = "RETRIEVAL_COUNT", nullable = false)
	private Long retrievalCount;
	
	@Column(name = "LAST_MODIFIED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(String dailyRate) {
		this.dailyRate = dailyRate;
	}

	public Date getFetchedOn() {
		return fetchedOn;
	}

	public void setFetchedOn(Date fetchedOn) {
		this.fetchedOn = fetchedOn;
	}

	public Long getRetrievalCount() {
		return retrievalCount;
	}

	public void setRetrievalCount(Long retrievalCount) {
		this.retrievalCount = retrievalCount;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
