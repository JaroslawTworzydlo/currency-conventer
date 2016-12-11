package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*; //importujmy statyczne motody z mockito

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import model.Currency;
import service.CurrencyCalculator;
import service.CurrencyRatesDownloader;

public class CurrencyCalculatorTest {
	
	private CurrencyCalculator classUnderTest;
	
	@Before
	public void setUp() {
		CurrencyRatesDownloader ratesDowlnoader = mock(CurrencyRatesDownloader.class);
		// metoda when w mocku
		when(ratesDowlnoader.downloadRate(Currency.PLN, Currency.USD)).thenReturn(new BigDecimal("4")); //zamokowane tylko dla tych par walut
		when(ratesDowlnoader.downloadRate(Currency.PLN, Currency.EUR)).thenReturn(new BigDecimal("4.2"));
		
		
		classUnderTest = new CurrencyCalculator(ratesDowlnoader);
			
	}
	@Test
	public void testCalculate1() throws Exception {
		
		BigDecimal amount = new BigDecimal("100");
		Currency from = Currency.PLN;
		Currency to = Currency.USD;
		
		BigDecimal result = classUnderTest.calculate(amount, from, to);
		
		assertEquals(new BigDecimal("400"), result);
	}
	@Test
	public void testCalculate2() throws Exception {
		
		BigDecimal amount = new BigDecimal("120");
		Currency from = Currency.PLN;
		Currency to = Currency.USD;
		
		BigDecimal result = classUnderTest.calculate(amount, from, to);
		
		assertEquals(new BigDecimal("480"), result);
	}
	
}
