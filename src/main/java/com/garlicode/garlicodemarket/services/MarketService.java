package com.garlicode.garlicodemarket.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garlicode.garlicodemarket.data.jpa.pojo.CorporateData;
import com.garlicode.garlicodemarket.providers.DataProvider;

@Service
public class MarketService {

	@Autowired
	private Function<String, DataProvider> dataProviderFactory;
	@Autowired
	private CorporateService corporateService;
	@Autowired
	private ScreenerService screenerService;

	public CorporateData getCorporateData(String ticker) throws Exception {
		// DataProvider provider = new YahooProvider(ticker);
		DataProvider provider = dataProviderFactory.apply(ticker);
		try {
			provider.execute();
		} catch (Exception e) {
			throw e;
		}
		CorporateData data = new CorporateData(provider);
		return data;
	}

	private Map<String, Object> readCriteriasFromJson(String conditions, int step) {
		Map<String, Object> criterias = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root;
		try {
			root = new ObjectMapper().readTree(conditions);
			switch (step) {
			case 1:
				if (root.has("sector")) {
					String[] sectors = mapper.convertValue(root.get("sector"), String[].class);
					criterias.put("sectors", sectors);
				}
				if (root.has("industry")) {
					String[] industries = mapper.convertValue(root.get("industry"), String[].class);
					criterias.put("industries", industries);
				}
				if (root.has("country")) {
					String[] countries = mapper.convertValue(root.get("country"), String[].class);
					criterias.put("countries", countries);
				}

				break;
			case 2:
				if (root.has("marketCap")) {
					switch (root.get("marketCapUnit").asText()) {
					case "k":
						criterias.put("marketCap", root.get("marketCap").asLong() * 1_000);
						break;
					case "M":
						criterias.put("marketCap", root.get("marketCap").asLong() * 1_000_000);
						break;
					case "B":
						criterias.put("marketCap", root.get("marketCap").asLong() * 1_000_000_000);
						break;
					default:
						break;
					}
					criterias.put("marketCapOp", root.get("marketCapOp").asText());
				}
				if (root.has("dividendYield")) {
					criterias.put("dividendYield", root.get("dividendYield").asDouble());
					criterias.put("dividendYieldOp", root.get("dividendYieldOp").asText());
				}
				if (root.has("payoutRatio")) {
					criterias.put("payoutRatio", root.get("payoutRatio").asDouble());
					criterias.put("payoutRatioOp", root.get("payoutRatioOp").asText());
				}
				if (root.has("peRatio")) {
					criterias.put("peRatio", root.get("peRatio").asDouble());
					criterias.put("peRatioOp", root.get("peRatioOp").asText());
				}
				if (root.has("revenue")) {
					switch (root.get("revenueUnit").asText()) {
					case "k":
						criterias.put("revenue", root.get("revenue").asLong() * 1_000);
						break;
					case "M":
						criterias.put("revenue", root.get("revenue").asLong() * 1_000_000);
						break;
					case "B":
						criterias.put("revenue", root.get("revenue").asLong() * 1_000_000_000);
						break;
					default:
						break;
					}
					criterias.put("revenueOp", root.get("revenueOp").asText());
				}
				if (root.has("netIncome")) {
					switch (root.get("netIncomeUnit").asText()) {
					case "k":
						criterias.put("netIncome", root.get("netIncome").asLong() * 1_000);
						break;
					case "M":
						criterias.put("netIncome", root.get("netIncome").asLong() * 1_000_000);
						break;
					case "B":
						criterias.put("netIncome", root.get("netIncome").asLong() * 1_000_000_000);
						break;
					default:
						break;
					}
					criterias.put("netIncomeOp", root.get("netIncomeOp").asText());
				}
				if (root.has("profitMargin")) {
					criterias.put("profitMargin", root.get("profitMargin").asDouble());
					criterias.put("profitMarginOp", root.get("profitMarginOp").asText());
				}
				if (root.has("eps")) {
					criterias.put("eps", root.get("eps").asDouble());
					criterias.put("epsOp", root.get("epsOp").asText());
				}
				if (root.has("debtToEquity")) {
					criterias.put("debtToEquity", root.get("debtToEquity").asDouble());
					criterias.put("debtToEquityOp", root.get("debtToEquityOp").asText());
				}
				break;
			default:
				break;
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return criterias;
	}

	private List<CorporateData> filterByKpis(List<CorporateData> corporates, Map<String, Object> criterias, int i) {

		return corporates
				.stream().filter(
						c -> c.getStatistics() != null
								? ((criterias
										.containsKey("marketCap")
												? ((String) (criterias.get("marketCapOp"))).equals("inf")
														? c.getStatistics()
																.getMarketCap() < ((long) criterias.get("marketCap"))
														: c.getStatistics()
																.getMarketCap() >= ((long) criterias.get("marketCap"))
												: true)
										&& (criterias
												.containsKey("dividendYield")
														? ((String) (criterias.get("dividendYieldOp"))).equals("inf")
																? c.getStatistics()
																		.getDividendYield() < ((double) criterias
																				.get("dividendYield"))
																: c.getStatistics()
																		.getDividendYield() >= ((double) criterias
																				.get("dividendYield"))
														: true)
										&& (criterias.containsKey("payoutRatio")
												? ((String) (criterias.get("payoutRatioOp"))).equals("sup")
														? c.getStatistics()
																.getPayoutRatio() > ((double) criterias
																		.get("payoutRatio"))
														: c.getStatistics()
																.getPayoutRatio() <= ((double) criterias
																		.get("payoutRatio"))
												: true)
										&& (criterias
												.containsKey("peRatio")
														? ((String) (criterias.get("peRatioOp"))).equals("sup")
																? c.getStatistics()
																		.getPeRatio() > ((double) criterias
																				.get("peRatio"))
																: c.getStatistics()
																		.getPeRatio() <= ((double) criterias
																				.get("peRatio"))
														: true)
										&& (criterias
												.containsKey("peRatio")
														? ((String) (criterias.get("peRatioOp"))).equals("sup")
																? c.getStatistics()
																		.getPeRatio() > ((double) criterias
																				.get("peRatio"))
																: c.getStatistics()
																		.getPeRatio() <= ((double) criterias
																				.get("peRatio"))
														: true)
										&& (criterias
												.containsKey("revenue")
														? ((String) (criterias.get("revenueOp"))).equals("inf")
																? c.getStatistics()
																		.getRevenue() < ((long) criterias
																				.get("revenue"))
																: c.getStatistics()
																		.getRevenue() >= ((long) criterias
																				.get("revenue"))
														: true)
										&& (criterias
												.containsKey("netIncome")
														? ((String) (criterias.get("netIncomeOp"))).equals("inf")
																? c.getStatistics()
																		.getNetIncome() < ((long) criterias
																				.get("netIncome"))
																: c.getStatistics()
																		.getNetIncome() >= ((long) criterias
																				.get("netIncome"))
														: true)
										&& (criterias.containsKey("profitMargin")
												? ((String) (criterias.get("profitMarginOp"))).equals("inf")
														? c.getStatistics()
																.getProfitMargin() < ((double) criterias
																		.get("profitMargin"))
														: c.getStatistics()
																.getProfitMargin() >= ((double) criterias
																		.get("profitMargin"))
												: true)
										&& (criterias.containsKey("eps") ? ((String) (criterias.get("epsOp"))).equals(
												"inf") ? c.getStatistics().getEps() < ((double) criterias.get("eps"))
														: c.getStatistics().getEps() >= ((double) criterias.get("eps"))
												: true)
										&& (criterias.containsKey("debtToEquity")
												? ((String) (criterias.get("debtToEquityOp"))).equals("sup")
														? c.getStatistics()
																.getDebtToEquity() > ((double) criterias
																		.get("debtToEquity"))
														: c.getStatistics()
																.getDebtToEquity() <= ((double) criterias
																		.get("debtToEquity"))
												: true))
								: false)
				.collect(Collectors.toList());
	}

	public List<CorporateData> screenTheMarket(String conditions) {
		Map<String, Object> criterias = this.readCriteriasFromJson(conditions, 1);
		List<String> s = this.getFilteredTickers(criterias);

		this.screenerService.setTickers(s);

		List<CorporateData> corpsData = this.screenerService.screnn(100);

		criterias = this.readCriteriasFromJson(conditions, 2);

		List<CorporateData> result = this.filterByKpis(corpsData, criterias, 2);

		return result;
	}

	private List<String> getFilteredTickers(Map<String, Object> criterias) {
		return this.corporateService
				.findAll().stream().filter(
						c -> (criterias.containsKey("countries")
								? (c.getCountry() != null
										? (Arrays.stream((String[]) criterias.get("countries"))
												.anyMatch(c.getCountry()::equals))
										: false)
								: true)
								&& (criterias.containsKey("sectors") ? (c.getSector() != null
										? (Arrays.stream((String[]) criterias.get("sectors")).anyMatch(
												c.getSector().getLabel()::equals))
										: false) : true)
								&& (criterias.containsKey("industries")
										? (c.getIndustry() != null
												? (Arrays.stream((String[]) criterias.get("industries"))
														.anyMatch(c.getIndustry().getLabel()::equals))
												: false)
										: true)

				).map(c -> c.getTicker()).collect(Collectors.toList());
	}
}
