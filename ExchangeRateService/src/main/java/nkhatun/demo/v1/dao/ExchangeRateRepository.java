package nkhatun.demo.v1.dao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nkhatun.demo.v1.domain.ExchangeRate;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
@Repository
public interface ExchangeRateRepository extends CrudRepository<ExchangeRate, Long>{

	@Query(value = "select CURRENCY_CODE,RETRIEVAL_COUNT from EXCHANGE_RATE where trunc(FETCHED_ON) = trunc(select max(FETCHED_ON) from EXCHANGE_RATE )", nativeQuery = true)
	public List<Object[]> getLatestCurrencyList();
	
	@Query(value = "SELECT * from EXCHANGE_RATE where CURRENCY_CODE = ? and trunc(FETCHED_ON) = trunc(select max(FETCHED_ON) from EXCHANGE_RATE )", nativeQuery = true)
	public Optional<ExchangeRate> getDailyExchangeRate(String currencyCode);
	
	@Query(value = "UPDATE EXCHANGE_RATE SET RETRIEVAL_COUNT  = ? WHERE CURRENCY_CODE = ? and trunc(FETCHED_ON) = trunc(select max(FETCHED_ON) from EXCHANGE_RATE )", nativeQuery = true)
	public ExchangeRate findLatestCurrencyRow(Long count, String currency);
}
