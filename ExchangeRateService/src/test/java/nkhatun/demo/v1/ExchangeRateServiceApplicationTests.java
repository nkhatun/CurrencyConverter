package nkhatun.demo.v1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nkhatun.demo.v1.controller.ExchangeRateController;
import nkhatun.demo.v1.dto.ConversionDto;
import nkhatun.demo.v1.service.IExchangeRateService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ExchangeRateController.class)
class ExchangeRateServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IExchangeRateService exchangeRateService;

	@BeforeAll
	public static void setup() {
	}

	@Test
	public void fetchCurrencyDetailsTest() throws Exception {
		String uri = "/api/v1/getCurrenciesList";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	public void fetchECBReferenceRateTest() throws Exception {
		String uri = "/api/v1/fetchECBReferenceRate";
		ConversionDto conversionDto = new ConversionDto();
		conversionDto.setConvertFrom("AED");
		conversionDto.setConvertTo("GBP");
		String inputJson = mapToJson(conversionDto);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		 assertEquals(200, status);
	}
	@Test
	public void convertCurrencyAmountTest() throws Exception {
		String uri = "/api/v1/convertCurrencyAmount";
		ConversionDto conversionDto = new ConversionDto();
		conversionDto.setConvertFrom("AED");
		conversionDto.setConvertTo("GBP");
		conversionDto.setConvertTo("151.03");
		String inputJson = mapToJson(conversionDto);
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		 assertEquals(200, status);
	}

	public String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

}
