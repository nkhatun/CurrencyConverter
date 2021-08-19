package nkhatun.demo.v1.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nkhatun.demo.v1.dao.ExchangeRateRepository;
import nkhatun.demo.v1.domain.ExchangeRate;
import nkhatun.demo.v1.dto.ConversionDto;
import nkhatun.demo.v1.dto.CurrencyDto;
import nkhatun.demo.v1.dto.DataDto;
import nkhatun.demo.v1.dto.ResponseDto;

@Service
public class ExchangeRateService implements IExchangeRateService {
	private HashMap<String, BigDecimal> exchangeRates;
	private static final String baseCurrency = "EUR";
	private static final String publicChartUrl = "https://www.xe.com/currencycharts/?from=";

	@Autowired
	private ExchangeRateRepository exchRepo;

	@Override
	public ResponseDto fetchCurrencyList() {
		List<Object[]> currencyList = exchRepo.getLatestCurrencyList();
		if (!currencyList.isEmpty()) {
			List<CurrencyDto> currencyDtoList = currencyList.stream()
					.map(code -> new CurrencyDto((String) code[0],
							Currency.getInstance((String) code[0]).getDisplayName(),
							((BigInteger) code[1]).longValue()))
					.collect(Collectors.toList());
			DataDto<CurrencyDto> data = new DataDto<CurrencyDto>((currencyDtoList));
			return new ResponseDto("success", data, "ECB Exchange Rate Currencies Are Fetched Successfully");
		}
		return new ResponseDto("failed", null, "No Currency Is Configured In ECB");
	}

	public boolean isValidCurrency(ConversionDto conversionDto) {
		try {
			Currency convertFrom = Currency.getInstance(conversionDto.getConvertFrom());
			Currency convertTo = Currency.getInstance(conversionDto.getConvertTo());
			if (convertFrom != null && convertTo != null) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	public Optional<ExchangeRate> convertEuroToOther(String convertFromEuro) {
		Optional<ExchangeRate> exchangeRateObjTo = exchRepo.getDailyExchangeRate(convertFromEuro);
		if (exchangeRateObjTo.isPresent()) {
			ExchangeRate exchangeRateObj = exchangeRateObjTo.get();
			updateRetrievalCount(exchangeRateObj);
			return exchangeRateObjTo;
		}
		return Optional.empty();
	}

	public Optional<BigDecimal> convertOtherToAny(ConversionDto conversionDto) {
		if (conversionDto.getConvertTo().equals(baseCurrency)) {
			Optional<ExchangeRate> exchangeRateObjTo = convertEuroToOther(conversionDto.getConvertFrom());
			if (exchangeRateObjTo.isPresent()) {
				BigDecimal dailyRate = new BigDecimal(exchangeRateObjTo.get().getDailyRate());
				return convertForOtherCurrency(new BigDecimal(1), dailyRate);
			}
			return Optional.empty();
		} else {
			Optional<ExchangeRate> exchangeRateObjFrom = convertEuroToOther(conversionDto.getConvertFrom());
			Optional<ExchangeRate> exchangeRateObjTo = convertEuroToOther(conversionDto.getConvertTo());
			if (exchangeRateObjFrom.isPresent() && exchangeRateObjTo.isPresent()) {
				BigDecimal exchRateFrom = new BigDecimal(exchangeRateObjFrom.get().getDailyRate());
				BigDecimal exchRateTo = new BigDecimal(exchangeRateObjTo.get().getDailyRate());

				return convertForOtherCurrency(exchRateTo, exchRateFrom);
			}
			return Optional.empty();
		}
	}

	@Override
	public ResponseDto fetchECBReferenceRate(ConversionDto conversionDto) {
		try {
			if (isValidCurrency(conversionDto)) {
				ConversionDto conversionDtoGenerated = conversionDto;
				if (conversionDto.getConvertFrom().equals(conversionDto.getConvertTo())) {
					conversionDtoGenerated.setRate(new BigDecimal(1));
					conversionDtoGenerated.setFetchedOn(new Date());
				} else if (conversionDto.getConvertFrom().equals(baseCurrency)) {
					Optional<ExchangeRate> exchangeRateOp = convertEuroToOther(conversionDto.getConvertTo());
					if (exchangeRateOp.isPresent()) {
						BigDecimal exchRateFromEuro = new BigDecimal(exchangeRateOp.get().getDailyRate());
						conversionDtoGenerated.setRate(exchRateFromEuro);
						conversionDtoGenerated.setFetchedOn(new Date());
					} else {
						return new ResponseDto("failed", null,
								conversionDto.getConvertTo() + ": Currency Is Not Registered With ECB");
					}
				} else {
					Optional<BigDecimal> dailyRate = convertOtherToAny(conversionDto);
					if (dailyRate.isPresent()) {
						conversionDtoGenerated.setRate(dailyRate.get());
						conversionDtoGenerated.setFetchedOn(new Date());
					} else {
						return new ResponseDto("failed", null, "Currency Is Not Registered With ECB");
					}
				}

				DataDto<ConversionDto> data = new DataDto<ConversionDto>(
						Collections.singletonList(conversionDtoGenerated));
				return new ResponseDto("success", data, "ECB Exchange Rate Fetched Successfully From: "
						+ conversionDto.getConvertFrom() + " To: " + conversionDto.getConvertTo());

			} else {
				return new ResponseDto("failed", null, "Invalid Currency Code");
			}
		} catch (Exception ex) {
			return new ResponseDto("failed", null, "Some Problem Occurred While Converting The Currencies");
		}
	}

	@Transactional
	public void updateRetrievalCount(ExchangeRate exchangeRateObj) {
		exchangeRateObj.setRetrievalCount(exchangeRateObj.getRetrievalCount() + 1);
		exchangeRateObj.setLastModified(new Date());
		exchRepo.save(exchangeRateObj);
	}

	private Optional<BigDecimal> convertForOtherCurrency(BigDecimal exchRateTo, BigDecimal exchRateFrom) {
		BigDecimal generated = (exchRateTo).divide(exchRateFrom, 8, RoundingMode.HALF_UP);
		return Optional.of(generated);
	}

	private Optional<BigDecimal> convertCurrencyToOther(ConversionDto conversionDto) {
		// rate from eur to convertFrom currency
		Optional<BigDecimal> fromRateOp = convertCurrencyToEur(conversionDto.getConvertFrom());
		// rate from eur to convertTo currency
		Optional<BigDecimal> toRateOp = convertCurrencyToEur(conversionDto.getConvertTo());
		if (fromRateOp.isPresent() && toRateOp.isPresent()) {
			BigDecimal generated = (toRateOp.get()).divide(fromRateOp.get(), 8, RoundingMode.HALF_UP);
			return Optional.of(generated);
		}
		return Optional.empty();
	}

	private Optional<BigDecimal> convertCurrencyToEur(String convertTo) {
		return exchangeRates.get(convertTo) != null ? Optional.of(exchangeRates.get(convertTo)) : Optional.empty();
	}

	@Override
	public ResponseDto convertAmountByECBRate(ConversionDto conversionDto) {
		try {
		BigDecimal amount = new BigDecimal(conversionDto.getAmount());
		if (amount != null) { 
			ResponseDto responseDto = fetchECBReferenceRate(conversionDto);
			if (responseDto.getStatus().equalsIgnoreCase("success")) {
				// Received the exchange rate for the currencies, calculating the amount below
				BigDecimal exchRate = ((ConversionDto) responseDto.getData().getDataItems().get(0)).getRate();
				BigDecimal exchAmnt = exchRate.multiply(amount);
				conversionDto.setRate(exchRate);
				conversionDto.setConvertedAmount(exchAmnt);
				conversionDto.setFetchedOn(new Date());
				DataDto<ConversionDto> data = new DataDto<ConversionDto>(Collections.singletonList(conversionDto));
				return new ResponseDto("success", data, "Amount Converted Successfully");
			} else {
				return responseDto;
			}
		} else {
			return new ResponseDto("failed", null, "Numeric Amount Is Required For Conversion");
		}
		}
		catch(Exception e) {
			return new ResponseDto("failed", null, "Amount Should Be Numeric For Conversion");
		}
	}

	@Override
	public ResponseDto retrieveChartLink(String convertFrom, String convertTo) {
		ConversionDto conversionDto = new ConversionDto();
		conversionDto.setConvertFrom(convertFrom);
		conversionDto.setConvertTo(convertTo);
		if (isValidCurrency(conversionDto)) {
			StringBuilder chartUrl = new StringBuilder(publicChartUrl + convertFrom + "&to=" + convertTo);
			conversionDto.setChartLink(chartUrl.toString());
			conversionDto.setFetchedOn(new Date());
			DataDto<ConversionDto> data = new DataDto<ConversionDto>(Collections.singletonList(conversionDto));
			return new ResponseDto("success", data, "Link For The Currency Pair Fetched Successfully");
		} else {
			return new ResponseDto("failed", null, "Invalid Currency Code");
		}
	}

	@Transactional
	public void saveExchageRateData(HashMap<String, BigDecimal> exchangeRates) {
		List<ExchangeRate> exchangeRateList = new ArrayList<ExchangeRate>();
		exchangeRates.entrySet().stream().forEach(pair -> {
			ExchangeRate exchangeRate = new ExchangeRate();
			exchangeRate.setCurrencyCode(pair.getKey());
			exchangeRate.setDailyRate(pair.getValue().toString());
			exchangeRate.setFetchedOn(new Date());
			exchangeRate.setRetrievalCount(0L);
			exchangeRateList.add(exchangeRate);
		});
		exchRepo.saveAll(exchangeRateList);
	}
}
