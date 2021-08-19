package nkhatun.demo.v1.crawler;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nkhatun.demo.v1.dao.ExchangeRateRepository;
import nkhatun.demo.v1.scheduler.ExchangeRateScheduler;
import nkhatun.demo.v1.service.IExchangeRateService;
@Service
public class ExchangeRateCrawlerService implements IExchangeRateCrawlerService {
	private static Logger logger = LoggerFactory.getLogger(ExchangeRateCrawlerService.class);

	@Autowired
	private IExchangeRateService exchService;
	private static final String PUBLISH_URL = "https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html";

	
	public Optional<HashMap<String, BigDecimal>> collectExchangeRates() {
		HashMap<String, BigDecimal> exchangeRates;

		try {
			// 1. Fetching the html code below
			Document document = Jsoup.connect(PUBLISH_URL).get();
			// 2. Parsing the html to get the currency conversion table
			Elements ratingsTable = document.getElementsByClass("forextable");
			exchangeRates = new HashMap<String, BigDecimal>();

			  for (Element ratings : ratingsTable) {
                  // Extract the currency code and rate
                  String currencyCode = ratings.getElementsByClass("currency").text();
                  String rate = ratings.getElementsByClass("rate").text();

                  String[] currencyCodeArr = currencyCode.split(" ");
                  String[] ratingsArr = rate.split(" ");

                  for(int i=0;i<currencyCodeArr.length;i++) {
                  	BigDecimal val = new BigDecimal(ratingsArr[i]); 
                  	exchangeRates.put(currencyCodeArr[i],val);
                  }
          		logger.debug("Currency:  " +currencyCode +"Rate: "+rate);
                  }
              System.out.println("Rates : "+exchangeRates);
        		logger.debug("Exchange Rate:  " +exchangeRates +" On: "+ LocalDateTime.now());

              return Optional.of(exchangeRates);

		} catch (IOException | IllegalArgumentException ex) {
            return Optional.empty();
		}
	}

	@Override
	public void saveExchangeRates() {
		Optional<HashMap<String, BigDecimal>>  exchangeRates = collectExchangeRates();
		if(exchangeRates.isPresent()) {
			// Save Exchange Rate Entry For A Day	
			try {
			exchService.saveExchageRateData(exchangeRates.get());
			}
			catch (Exception ex) {
        		logger.debug("Execption While Saving Exchange Rate Data");
			}
			
		}
	}
	
	

}
