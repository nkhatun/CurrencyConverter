package nkhatun.demo.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nkhatun.demo.v1.dto.ConversionDto;
import nkhatun.demo.v1.dto.ResponseDto;
import nkhatun.demo.v1.service.IExchangeRateService;

@RestController
@RequestMapping(value = "/api/v1/")
public class ExchangeRateController {
	@Autowired
	private IExchangeRateService erService;

	@RequestMapping(value = "getCurrenciesList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto getECBCurrencies() {
		return erService.fetchCurrencyList();
	}

	@RequestMapping(value = "fetchECBReferenceRate", method = RequestMethod.POST, consumes = "application/json")
	public ResponseDto fetchECBReferenceRate(@Valid @RequestBody ConversionDto conversionDto) {
		return erService.fetchECBReferenceRate(conversionDto);
	}

	@PostMapping("convertCurrencyAmount")
	public ResponseDto convertAmountByECBRate(@Valid @RequestBody ConversionDto conversionDto) {
		return erService.convertAmountByECBRate(conversionDto);
	}

	@GetMapping("retrieveChartLink")
	public ResponseDto retrieveChartLink(@RequestParam String from, @RequestParam String to) {
		return erService.retrieveChartLink(from, to);
	}
}
