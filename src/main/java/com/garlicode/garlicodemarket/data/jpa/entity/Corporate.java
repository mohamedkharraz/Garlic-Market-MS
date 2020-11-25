package com.garlicode.garlicodemarket.data.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity()
public class Corporate {

    @Id
    private String ticker;
    private String name;
    private String country;
    private String logo;
    @ManyToOne
    @JoinColumn(name = "currency_code")
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;
    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;


    public Corporate() {
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

	@Override
	public String toString() {
		return "Corporate [ticker=" + ticker + ", name=" + name + ", country=" + country + ", logo=" + logo
				+ ", currency=" + currency + ", sector=" + sector + ", industry=" + industry + "]";
	}
    
    
}
