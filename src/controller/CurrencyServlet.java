package controller;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Currency;
import service.CurrencyCalculator;
import service.CurrencyRatesDownloader;
@WebServlet({"/", "/currencies"})
public class CurrencyServlet extends HttpServlet {
	
	private CurrencyCalculator currencyCalculator;
	
	public CurrencyServlet() {
		CurrencyRatesDownloader ratesDownloader = new CurrencyRatesDownloader(); //CRDl nie ma ¿adnych zale¿noœci
		currencyCalculator = new CurrencyCalculator(ratesDownloader);
	}
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.setAttribute("currencies", Currency.values());
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	BigDecimal amount = new BigDecimal(req.getParameter("amount"));
    	Currency fromCurrency = Currency.valueOf(req.getParameter("fromCurrency")); //valueof - istnieje dla ka¿dego enuma, pozwala na zamiane Stringa na enum
    	Currency toCurrency = Currency.valueOf(req.getParameter("toCurrency"));
    	
    	BigDecimal result = currencyCalculator.calculate(amount, fromCurrency, toCurrency);
    	req.setAttribute("result", result.setScale(2, RoundingMode.HALF_UP)); //uzytkownik widzi kwote
    	req.setAttribute("toCurrency", toCurrency); //uzytkownik widzi walute
    	req.setAttribute("amount", amount);
    	req.setAttribute("fromCurrency", fromCurrency);
    	req.setAttribute("currencies", Currency.values());
    	
//    	System.out.println(req.getParameter("amount"));
//        System.out.println(req.getParameter("fromCurrency")); tylko po to, zeby zobaczyc czy sie dobrze wyswietla na konsoli
//        System.out.println(req.getParameter("toCurrency"));
//        
        
        
        //resp.sendRedirect(req.getContextPath() + "/");
    	RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(req, resp);
    }
    
}