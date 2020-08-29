package com.garlicode.garlicodemarket.providers;

import com.garlicode.garlicodemarket.data.jpa.entity.Currency;
import com.garlicode.garlicodemarket.data.jpa.entity.Industry;
import com.garlicode.garlicodemarket.data.jpa.entity.Sector;

public interface DataProvider {

    void execute();

    /*
        Corporate Informations
     */
    String getTicker();

    String getName();

    String getCountry();

    String getLogo();

    Currency getCurrency();

    Sector getSector();

    Industry getIndustry();

    /*
        Corporate Financial Data
     */
    double getMarketCap();

    String getMarketCapFmt();

    default double getPeRatio(){
        return this.getEps() != 0 ? this.getPrice() / this.getEps() : 0;
    }

    long getRevenue();

    String getRevenueFmt();

    double getNetIncome();

    String getNetIncomeFmt();

    double getEps();

    String getEpsFmt();

    double getDividendRate();

    default double getDividendYield() {
        return this.getPrice() != 0
                ? this.getDividendRate() / this.getPrice()
                : 0;
    }

    default double getProfitMargin(){
        return this.getRevenue() != 0
                ? this.getNetIncome() / this.getRevenue()
                : 0;
    }

    default double getPayoutRatio() {
        return this.getNetIncome() != 0
                ? this.getDividendRate() * this.getSharesOutstanding() / this.getNetIncome()
                : 0;
    }

    double getPrice();

    long getSharesOutstanding();

    String getSharesOutstandingFmt();

    double getDayLow();

    double getDayHigh();

    double getYearLow();

    double getYearHigh();
}
