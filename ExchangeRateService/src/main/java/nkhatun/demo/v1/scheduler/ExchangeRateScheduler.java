package nkhatun.demo.v1.scheduler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import nkhatun.demo.v1.crawler.IExchangeRateCrawlerService;

@Component
public class ExchangeRateScheduler {
	private static Logger logger = LoggerFactory.getLogger(ExchangeRateScheduler.class);

	@Autowired
	private IExchangeRateCrawlerService crawlerService;

	//Commenting the below cron expression, to collect data while running the application
	//@Scheduled(cron = "${crawler.frequency}")
	public void scheduleExchangeRate() throws IOException {
		logger.debug("Start Exchange Report Scheduler: " + LocalDateTime.now());

		crawlerService.saveExchangeRates();
		logger.debug("End Exchange Report Scheduler: " + LocalDateTime.now());
	}

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.debug("Calling the scheduler on application event");
		try {
			scheduleExchangeRate();
		} catch (IOException e) {
			logger.debug("An exception occurred during exchange rate collection");
		}
	}

}
