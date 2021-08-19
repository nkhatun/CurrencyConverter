package nkhatun.demo.v1.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import nkhatun.demo.v1.dto.ConversionDto;
import nkhatun.demo.v1.dto.ResponseDto;

public interface IExchangeRateService {

	ResponseDto fetchCurrencyList();

	ResponseDto fetchECBReferenceRate(ConversionDto conversionDto);

	ResponseDto convertAmountByECBRate(ConversionDto conversionDto);

	ResponseDto retrieveChartLink(String from, String to);

	void saveExchageRateData(HashMap<String, BigDecimal> exchangeRates);

}
