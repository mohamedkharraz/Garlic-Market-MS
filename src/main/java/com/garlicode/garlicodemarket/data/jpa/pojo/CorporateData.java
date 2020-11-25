package com.garlicode.garlicodemarket.data.jpa.pojo;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.providers.DataProvider;

public class CorporateData {

	private Corporate corporate;
	private CorporateStatistics statistics;

	public CorporateData() {
	}

	public CorporateData(Corporate corporate, Map<String, JsonNode> dataMap) {
		this.corporate = corporate;
		this.statistics = new CorporateStatistics();

		if (dataMap != null) {
			if (dataMap.containsKey("dayHigh"))
				this.statistics.setDayHigh(dataMap.get("dayHigh").asDouble());
			if (dataMap.containsKey("dayLow"))
				this.statistics.setDayLow(dataMap.get("dayLow").asDouble());
			if (dataMap.containsKey("eps"))
				this.statistics.setEps(dataMap.get("eps").asDouble());
			if (dataMap.containsKey("epsFmt"))
				this.statistics.setEpsFmt(dataMap.get("epsFmt").asText());
			if (dataMap.containsKey("marketCap"))
				this.statistics.setMarketCap(dataMap.get("marketCap").asLong());
			if (dataMap.containsKey("marketCapFmt"))
				this.statistics.setMarketCapFmt(dataMap.get("marketCapFmt").asText());
			if (dataMap.containsKey("netIncome"))
				this.statistics.setNetIncome(dataMap.get("netIncome").asDouble());
			if (dataMap.containsKey("netIncomeFmt"))
				this.statistics.setNetIncomeFmt(dataMap.get("netIncomeFmt").asText());
			if (dataMap.containsKey("price"))
				this.statistics.setPrice(dataMap.get("price").asDouble());
			if (dataMap.containsKey("dividendRate"))
				this.statistics.setDividendRate(dataMap.get("dividendRate").asDouble());
			if (dataMap.containsKey("revenue"))
				this.statistics.setRevenue(dataMap.get("revenue").asLong());
			if (dataMap.containsKey("revenueFmt"))
				this.statistics.setRevenueFmt(dataMap.get("revenueFmt").asText());
			if (dataMap.containsKey("sharesOutstanding"))
				this.statistics.setSharesOutstanding(dataMap.get("sharesOutstanding").asLong());
			if (dataMap.containsKey("sharesOutstandingFmt"))
				this.statistics.setSharesOutstandingFmt(dataMap.get("sharesOutstandingFmt").asText());
			if (dataMap.containsKey("yearHigh"))
				this.statistics.setYearHigh(dataMap.get("yearHigh").asDouble());
			if (dataMap.containsKey("yearLow"))
				this.statistics.setYearLow(dataMap.get("yearLow").asDouble());
			if (dataMap.containsKey("totalStockholderEquity"))
				this.statistics.setTotalStockholderEquity(dataMap.get("totalStockholderEquity").asLong());
			if (dataMap.containsKey("totalLiabilities"))
				this.statistics.setTotalLiabilities(dataMap.get("totalLiabilities").asLong());

			this.statistics.setDividendYield(
					this.statistics.getPrice() != 0 ? this.statistics.getDividendRate() / this.statistics.getPrice()
							: 0);
			this.statistics.setProfitMargin(
					this.statistics.getRevenue() != 0 ? this.statistics.getNetIncome() / this.statistics.getRevenue()
							: 0);
			this.statistics.setPayoutRatio(
					this.statistics.getNetIncome() != 0
							? this.statistics.getDividendRate() * this.statistics.getSharesOutstanding()
									/ this.statistics.getNetIncome()
							: 0);
			this.statistics.setPeRatio(
					this.statistics.getEps() != 0 ? this.statistics.getPrice() / this.statistics.getEps() : 0);
			this.statistics.setDebtToEquity(this.statistics.getTotalLiabilities() != 0
					? this.statistics.getTotalLiabilities() / (double) this.statistics.getTotalStockholderEquity()
					: Double.MAX_VALUE);
		}
	}

	public CorporateData(DataProvider provider) {
		this.corporate = new Corporate();
		this.statistics = new CorporateStatistics();

		this.corporate.setCountry(provider.getCountry());
		this.corporate.setLogo(provider.getLogo());
		this.corporate.setName(provider.getName());
		this.corporate.setTicker(provider.getTicker());
		this.corporate.setCurrency(provider.getCurrency());
		this.corporate.setSector(provider.getSector());
		this.corporate.setIndustry(provider.getIndustry());

		this.statistics.setDayHigh(provider.getDayHigh());
		this.statistics.setDayLow(provider.getDayLow());
		this.statistics.setDividendRate(provider.getDividendRate());
		this.statistics.setDividendYield(provider.getDividendYield());
		this.statistics.setEps(provider.getEps());
		this.statistics.setEpsFmt(provider.getEpsFmt());
		this.statistics.setMarketCap(provider.getMarketCap());
		this.statistics.setMarketCapFmt(provider.getMarketCapFmt());
		this.statistics.setNetIncome(provider.getNetIncome());
		this.statistics.setNetIncomeFmt(provider.getNetIncomeFmt());
		this.statistics.setPayoutRatio(provider.getPayoutRatio());
		this.statistics.setPeRatio(provider.getPeRatio());
		this.statistics.setPrice(provider.getPrice());
		this.statistics.setProfitMargin(provider.getProfitMargin());
		this.statistics.setRevenue(provider.getRevenue());
		this.statistics.setRevenueFmt(provider.getRevenueFmt());
		this.statistics.setSharesOutstanding(provider.getSharesOutstanding());
		this.statistics.setSharesOutstandingFmt(provider.getSharesOutstandingFmt());
		this.statistics.setYearHigh(provider.getYearHigh());
		this.statistics.setYearLow(provider.getYearLow());
		this.statistics.setTotalStockholderEquity(provider.getTotalStockholderEquity());
		this.statistics.setTotalLiabilities(provider.getTotalLiabilities());
		this.statistics.setDebtToEquity(provider.getDebtToEquity());
	}

	public Corporate getCorporate() {
		return corporate;
	}

	public void setCorporate(Corporate corporate) {
		this.corporate = corporate;
	}

	public CorporateStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(CorporateStatistics statistics) {
		this.statistics = statistics;
	}

	@Override
	public String toString() {
		return "CorporateData [corporate=" + corporate + ", statistics=" + statistics + "]";
	}

}
