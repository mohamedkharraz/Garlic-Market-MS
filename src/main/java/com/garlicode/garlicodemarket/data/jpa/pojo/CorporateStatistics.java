package com.garlicode.garlicodemarket.data.jpa.pojo;

public class CorporateStatistics {

    private long marketCap;
    private String marketCapFmt;
    private double peRatio;
    private long revenue;
    private String revenueFmt;
    private double netIncome;
    private String netIncomeFmt;
    private double eps;
    private String epsFmt;
    private double dividendRate;
    private double dividendYield;
    private double profitMargin;
    private double payoutRatio;
    private double price;
    private long sharesOutstanding;
    private String sharesOutstandingFmt;
    private double dayLow;
    private double dayHigh;
    private double yearLow;
    private double yearHigh;
    private long totalStockholderEquity;
    private long totalLiabilities;
    private double debtToEquity;

    public CorporateStatistics() {
    }

    public long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(long marketCap) {
        this.marketCap = marketCap;
    }

    public String getMarketCapFmt() {
        return marketCapFmt;
    }

    public void setMarketCapFmt(String marketCapFmt) {
        this.marketCapFmt = marketCapFmt;
    }

    public double getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(double peRatio) {
        this.peRatio = peRatio;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public String getRevenueFmt() {
        return revenueFmt;
    }

    public void setRevenueFmt(String revenueFmt) {
        this.revenueFmt = revenueFmt;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public String getNetIncomeFmt() {
        return netIncomeFmt;
    }

    public void setNetIncomeFmt(String netIncomeFmt) {
        this.netIncomeFmt = netIncomeFmt;
    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public String getEpsFmt() {
        return epsFmt;
    }

    public void setEpsFmt(String epsFmt) {
        this.epsFmt = epsFmt;
    }

    public double getDividendRate() {
        return dividendRate;
    }

    public void setDividendRate(double dividendRate) {
        this.dividendRate = dividendRate;
    }

    public double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getPayoutRatio() {
        return payoutRatio;
    }

    public void setPayoutRatio(double payoutRatio) {
        this.payoutRatio = payoutRatio;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(long sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public String getSharesOutstandingFmt() {
        return sharesOutstandingFmt;
    }

    public void setSharesOutstandingFmt(String sharesOutstandingFmt) {
        this.sharesOutstandingFmt = sharesOutstandingFmt;
    }

    public double getDayLow() {
        return dayLow;
    }

    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getYearLow() {
        return yearLow;
    }

    public void setYearLow(double yearLow) {
        this.yearLow = yearLow;
    }

    public double getYearHigh() {
        return yearHigh;
    }

    public void setYearHigh(double yearHigh) {
        this.yearHigh = yearHigh;
    }

    public long getTotalStockholderEquity() {
        return totalStockholderEquity;
    }

    public void setTotalStockholderEquity(long totalStockholderEquity) {
        this.totalStockholderEquity = totalStockholderEquity;
    }

    public long getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(long totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public double getDebtToEquity() {
        return debtToEquity;
    }

    public void setDebtToEquity(double debtToEquity) {
        this.debtToEquity = debtToEquity;
    }

	@Override
	public String toString() {
		return "CorporateStatistics [marketCap=" + marketCap + ", marketCapFmt=" + marketCapFmt + ", peRatio=" + peRatio
				+ ", revenue=" + revenue + ", revenueFmt=" + revenueFmt + ", netIncome=" + netIncome + ", netIncomeFmt="
				+ netIncomeFmt + ", eps=" + eps + ", epsFmt=" + epsFmt + ", dividendRate=" + dividendRate
				+ ", dividendYield=" + dividendYield + ", profitMargin=" + profitMargin + ", payoutRatio=" + payoutRatio
				+ ", price=" + price + ", sharesOutstanding=" + sharesOutstanding + ", sharesOutstandingFmt="
				+ sharesOutstandingFmt + ", dayLow=" + dayLow + ", dayHigh=" + dayHigh + ", yearLow=" + yearLow
				+ ", yearHigh=" + yearHigh + ", totalStockholderEquity=" + totalStockholderEquity
				+ ", totalLiabilities=" + totalLiabilities + ", debtToEquity=" + debtToEquity + "]";
	}
    
    
}
