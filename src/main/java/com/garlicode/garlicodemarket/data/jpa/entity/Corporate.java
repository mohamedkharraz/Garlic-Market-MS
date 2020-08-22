package com.garlicode.garlicodemarket.data.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity()
public class Corporate {

    @Id
    private String ticker;
    private String name;
    private String country;
    private String logo;

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
}
