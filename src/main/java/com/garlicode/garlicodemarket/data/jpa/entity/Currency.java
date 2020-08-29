package com.garlicode.garlicodemarket.data.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity()
public class Currency {

    @Id
    private String isoCode;
    private String issuer;
    private String label;
    private String symbol;

    public Currency() {
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
