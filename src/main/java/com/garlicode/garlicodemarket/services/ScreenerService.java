package com.garlicode.garlicodemarket.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.data.jpa.pojo.CorporateData;
import com.garlicode.garlicodemarket.providers.YahooProvider;

@Service
public class ScreenerService {

	@Autowired
	private CorporateService corporateService;

	private List<String> tickers;

	public List<CorporateData> screnn(int threads) {
		List<CorporateData> result = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		List<Callable<CorporateData>> callables = new ArrayList<>();
		for (String ticker : this.getTickers()) {
			Corporate corporate = this.corporateService.getCorporateByTicker(ticker).get();
			callables.add(new ScreenerExecuter(corporate));
		}
		try {
			List<Future<CorporateData>> futures = executor.invokeAll(callables);
			for (Future<CorporateData> future : futures)
				result.add(future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static class ScreenerExecuter implements Callable<CorporateData> {
		
		private static int cpt = 0;

		private Corporate corporate;

		public ScreenerExecuter(Corporate corporate) {
			this.corporate = corporate;
		}

		@Override
		public CorporateData call() throws Exception {
			RestTemplate rest = new RestTemplate();
			ObjectMapper mapper = new ObjectMapper();
			Map<String, JsonNode> resultMap = new HashMap<>();

			String urlV7 = String.format(YahooProvider.YAHOO_V7, this.corporate.getTicker());
			String urlV10 = String.format(YahooProvider.YAHOO_V10, this.corporate.getTicker());

			String v7Response = null;
			JsonNode v7Root = null;
			String v10Response = null;
			JsonNode v10Root = null;
			try {
				v7Response = rest.getForObject(urlV7, String.class);
				v7Root = mapper.readTree(v7Response);
				Map<String, JsonNode> v7Nodes = new JsonFlattener(v7Root).flatten();
				YahooProvider.v7KeysMap.forEach((key, value) -> {
					if (v7Nodes.containsKey(key))
						resultMap.put(value, v7Nodes.get(key));
				});
				
				v10Response = rest.getForObject(urlV10, String.class);
				v10Root = mapper.readTree(v10Response);
				
				Map<String, JsonNode> v10Nodes = new JsonFlattener(v10Root).flatten();
				YahooProvider.v10KeysMap.forEach((key, value) -> {
					if (v10Nodes.containsKey(key))
						resultMap.put(value, v10Nodes.get(key));
				});
				
				return new CorporateData(corporate, resultMap);
				
			} catch (HttpClientErrorException e) {
				System.out.println("Http Error: " + this.corporate.getTicker() + " => " + (++cpt));
				//e.printStackTrace();
			}
			
			return new CorporateData(corporate, null);

			
		}

	}

	public List<String> getTickers() {
		return tickers;
	}

	public void setTickers(List<String> tickers) {
		this.tickers = tickers;
	}

}
