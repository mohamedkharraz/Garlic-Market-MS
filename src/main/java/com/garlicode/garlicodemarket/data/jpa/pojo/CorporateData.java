package com.garlicode.garlicodemarket.data.jpa.pojo;

import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.providers.DataProvider;

public class CorporateData {

    private Corporate corporate;
    private CorporateStatistics statistics;

    public CorporateData() {
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
}
