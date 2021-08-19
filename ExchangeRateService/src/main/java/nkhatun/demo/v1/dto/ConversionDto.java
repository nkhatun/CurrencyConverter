package nkhatun.demo.v1.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversionDto {
	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	private String convertFrom;
	@JsonProperty(required = true)
	@NotEmpty
	@NotBlank
	private String convertTo;
	private BigDecimal rate;
	private String amount;
	private BigDecimal convertedAmount;
	private Date fetchedOn;
	private String chartLink;

	public String getConvertFrom() {
		return convertFrom;
	}
	public void setConvertFrom(String convertFrom) {
		this.convertFrom = convertFrom;
	}
	public String getConvertTo() {
		return convertTo;
	}
	public void setConvertTo(String convertTo) {
		this.convertTo = convertTo;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public BigDecimal getConvertedAmount() {
		return convertedAmount;
	}
	public void setConvertedAmount(BigDecimal convertedAmount) {
		this.convertedAmount = convertedAmount;
	}
	public Date getFetchedOn() {
		return fetchedOn;
	}
	public void setFetchedOn(Date fetchedOn) {
		this.fetchedOn = fetchedOn;
	}
	public String getChartLink() {
		return chartLink;
	}
	public void setChartLink(String chartLink) {
		this.chartLink = chartLink;
	}
}
