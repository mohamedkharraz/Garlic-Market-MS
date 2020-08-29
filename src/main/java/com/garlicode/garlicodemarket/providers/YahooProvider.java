package com.garlicode.garlicodemarket.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlicode.garlicodemarket.data.jpa.entity.Corporate;
import com.garlicode.garlicodemarket.data.jpa.entity.Currency;
import com.garlicode.garlicodemarket.data.jpa.entity.Industry;
import com.garlicode.garlicodemarket.data.jpa.entity.Sector;
import com.garlicode.garlicodemarket.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.transaction.NotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class YahooProvider implements DataProvider {

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CorporateService corporateService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private IndustryService industryService;

    private static final String YAHOO_V7 = "https://query2.finance.yahoo.com/v7/finance/quote?formatted=true&symbols=%s";
    private static final String YAHOO_V10 = "https://query2.finance.yahoo.com/v10/finance/quoteSummary/%s?formatted=true&" +
            "modules=assetProfile," +
            "financialData," +
            "defaultKeyStatistics," /*+
            "incomeStatementHistory," +
            "calendarEvents," +
            "cashflowStatementHistory," +
            "balanceSheetHistory"*/;

    Map<String, JsonNode> dataMap;

    private final ConcurrentMap<String, String> v7KeysMap = new ConcurrentHashMap<>() {{
        this.put("quoteResponse.result.0.shortName", "name");
        this.put("quoteResponse.result.0.currency", "currency");
        this.put("quoteResponse.result.0.marketCap.raw", "marketCap");
        this.put("quoteResponse.result.0.marketCap.fmt", "marketCapFmt");
        this.put("quoteResponse.result.0.trailingAnnualDividendRate.raw", "dividendRate");
        this.put("quoteResponse.result.0.sharesOutstanding.raw", "sharesOutstanding");
        this.put("quoteResponse.result.0.sharesOutstanding.fmt", "sharesOutstandingFmt");
        this.put("quoteResponse.result.0.regularMarketDayLow.raw", "dayLow");
        this.put("quoteResponse.result.0.regularMarketDayHigh.raw", "dayHigh");
        this.put("quoteResponse.result.0.fiftyTwoWeekLow.raw", "yearLow");
        this.put("quoteResponse.result.0.fiftyTwoWeekHigh.raw", "yearHigh");
    }};

    private final ConcurrentMap<String, String> v10KeysMap = new ConcurrentHashMap<>() {{
        this.put("quoteSummary.result.0.assetProfile.country", "country");
        this.put("quoteSummary.result.0.financialData.totalRevenue.raw", "revenue");
        this.put("quoteSummary.result.0.financialData.totalRevenue.fmt", "revenueFmt");
        this.put("quoteSummary.result.0.defaultKeyStatistics.netIncomeToCommon.raw", "netIncome");
        this.put("quoteSummary.result.0.defaultKeyStatistics.netIncomeToCommon.fmt", "netIncomeFmt");
        this.put("quoteSummary.result.0.financialData.currentPrice.raw", "price");
        this.put("quoteSummary.result.0.defaultKeyStatistics.trailingEps.raw", "eps");
        this.put("quoteSummary.result.0.defaultKeyStatistics.trailingEps.fmt", "epsFmt");
        this.put("quoteSummary.result.0.assetProfile.sector", "sector");
        this.put("quoteSummary.result.0.assetProfile.industry", "industry");

    }};

    protected String ticker;
    protected Optional<Corporate> corporate;

    public YahooProvider(String ticker) {
        this.ticker = ticker;
        this.dataMap = new HashMap<>();
    }

    public void execute() {
        this.corporate = this.corporateService.getCorporateByTicker(ticker);
        Thread v7Thread = new Thread(new APIExecuter(7), "YahooAPI7Thread");
        Thread v10Thread = new Thread(new APIExecuter(10), "YahooAPI10Thread");
        v7Thread.start();
        v10Thread.start();
        try {
            v7Thread.join();
            v10Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class APIExecuter implements Runnable {

        private final int version;

        public APIExecuter(int version) {
            this.version = version;
        }

        @Override
        public void run() {

            try {
                RestTemplate rest = new RestTemplate();
                String url;
                Map<String, String> apiKeys;
                Map<String, JsonNode> nodes;
                switch (this.version) {
                    case 7:
                        url = String.format(YAHOO_V7, ticker);
                        apiKeys = v7KeysMap;
                        break;
                    case 10:
                        url = String.format(YAHOO_V10, ticker);
                        apiKeys = v10KeysMap;
                        break;
                    default:
                        throw new NotSupportedException();
                }
                String response = rest.getForObject(url, String.class);
                JsonNode root = new ObjectMapper().readTree(response);

                nodes = new JsonFlattener(root).flatten();
                apiKeys.forEach((key, value) -> {
                    if (nodes.containsKey(key))
                        dataMap.put(value, nodes.get(key));
                });
            } catch (JsonProcessingException | NotSupportedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public String getTicker() {
        return this.ticker;
    }

    @Override
    public String getName() {
        return this.dataMap.containsKey("name") ? this.dataMap.get("name").asText() : "N/A";
    }

    @Override
    public String getCountry() {
        return this.dataMap.containsKey("country") ? this.dataMap.get("country").asText() : "N/A";
    }

    @Override
    public String getLogo() {
        return null;
    }

    @Override
    public Currency getCurrency() {
        Optional<Currency> currency;
        if (this.corporate.isPresent())
            if ((currency = this.currencyService.getCurrency(corporate.get().getCurrency().getIsoCode())).isPresent())
                return currency.get();
        return this.dataMap.containsKey("currency")
                ? (currency = this.currencyService.getCurrency(this.dataMap.get("currency").asText())).isPresent()
                ? currency.get()
                : this.currencyService.addCurrency(this.dataMap.get("currency").asText()) : null;
    }


    @Override
    public Sector getSector() {
        Optional<Sector> sector;
        if (this.corporate.isPresent())
            if ((sector = this.sectorService.getSectorByLabel(corporate.get().getSector().getLabel())).isPresent())
                return sector.get();
        return this.dataMap.containsKey("sector")
                ? (sector = this.sectorService.getSectorByLabel(this.dataMap.get("sector").asText())).isPresent()
                ? sector.get()
                : this.sectorService.addSector(this.dataMap.get("sector").asText()) : null;
    }

    @Override
    public Industry getIndustry() {
        Optional<Industry> industry;
        if (this.corporate.isPresent())
            if ((industry = this.industryService.getIndustryByLabel(corporate.get().getIndustry().getLabel())).isPresent())
                return industry.get();
        return this.dataMap.containsKey("industry")
                ? (industry = this.industryService.getIndustryByLabel(this.dataMap.get("industry").asText())).isPresent()
                ? industry.get()
                : this.industryService.addIndustry(this.dataMap.get("industry").asText()) : null;
    }

    @Override
    public double getMarketCap() {
        return this.dataMap.containsKey("marketCap") ? this.dataMap.get("marketCap").asDouble() : 0;
    }

    @Override
    public String getMarketCapFmt() {
        return this.dataMap.containsKey("marketCapFmt") ? this.dataMap.get("marketCapFmt").asText() : "N/A";
    }

    @Override
    public long getRevenue() {
        return this.dataMap.containsKey("revenue") ? this.dataMap.get("revenue").asLong() : 0;
    }

    @Override
    public String getRevenueFmt() {
        return this.dataMap.containsKey("revenueFmt") ? this.dataMap.get("revenueFmt").asText() : "N/A";
    }

    @Override
    public double getNetIncome() {
        return this.dataMap.containsKey("netIncome") ? this.dataMap.get("netIncome").asDouble() : 0;
    }

    @Override
    public String getNetIncomeFmt() {
        return this.dataMap.containsKey("netIncomeFmt") ? this.dataMap.get("netIncomeFmt").asText() : "N/A";
    }

    @Override
    public double getEps() {
        return this.dataMap.containsKey("eps") ? this.dataMap.get("eps").asDouble() : 0;
    }

    @Override
    public String getEpsFmt() {
        return this.dataMap.containsKey("epsFmt") ? this.dataMap.get("epsFmt").asText() : "N/A";
    }

    @Override
    public double getDividendRate() {
        return this.dataMap.containsKey("dividendRate") ? this.dataMap.get("dividendRate").asDouble() : 0;
    }

    @Override
    public double getPrice() {
        return this.dataMap.containsKey("price") ? this.dataMap.get("price").asDouble() : 0;
    }

    @Override
    public long getSharesOutstanding() {
        return this.dataMap.containsKey("sharesOutstanding") ? this.dataMap.get("sharesOutstanding").asLong() : 0;
    }

    @Override
    public String getSharesOutstandingFmt() {
        return this.dataMap.containsKey("sharesOutstandingFmt") ? this.dataMap.get("sharesOutstandingFmt").asText() : "N/A";
    }

    @Override
    public double getDayLow() {
        return this.dataMap.containsKey("dayLow") ? this.dataMap.get("dayLow").asDouble () : 0;
    }

    @Override
    public double getDayHigh() {
        return this.dataMap.containsKey("dayHigh") ? this.dataMap.get("dayHigh").asDouble() : 0;
    }

    @Override
    public double getYearLow() {
        return this.dataMap.containsKey("yearLow") ? this.dataMap.get("yearLow").asDouble() : 0;
    }

    @Override
    public double getYearHigh() {
        return this.dataMap.containsKey("yearHigh") ? this.dataMap.get("yearHigh").asDouble() : 0;
    }


}
