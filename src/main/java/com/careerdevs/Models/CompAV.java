package com.careerdevs.Models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompAV {

    private String Symbol;
    private String AssetType;
    private String Name;
    private String Description;
    private String Address;
    private String marketCapitalization;
    private String dividendDate;

    public String getMarketCapitalization() {
        return marketCapitalization;
    }

    @JsonProperty ("MarketCapitalization")
    public void setMarketCapitalization(String marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public String getDividendDate() {
        return dividendDate;
    }

    @JsonProperty ("DividendDate")
    public void setDividendDate(String dividendDate) {
        this.dividendDate = dividendDate;
    }

    public String getSymbol() {
        return Symbol;
    }

    @JsonProperty("Symbol")
    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getAssetType() {
        return AssetType;
    }

    @JsonProperty("AssetType")
    public void setAssetType(String assetType) {
        AssetType = assetType;
    }

    public String getName() {
        return Name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        Description = description;
    }

    public String getAddress() {
        return Address;
    }

    @JsonProperty("Address")
    public void setAddress(String address) {
        Address = address;
    }

    public static CompAV feature5 (String symbol, String assetType, String name, String description, String address) {
        CompAV newComp = new CompAV();

        newComp.setAddress(address);
        newComp.setAssetType(assetType);
        newComp.setName(name);
        newComp.setSymbol(symbol);
        newComp.setDescription(description);

        return newComp;
    }

    public static CompAV feature6 (String symbol, String name, String marketCapitalization) {
        CompAV newComp = new CompAV();

        newComp.setName(name);
        newComp.setSymbol(symbol);
        newComp.setMarketCapitalization(marketCapitalization);

        return newComp;
    }

    public static CompAV feature7 (String symbol, String name, String dividendDate) {
        CompAV newComp = new CompAV();

        newComp.setName(name);
        newComp.setSymbol(symbol);
        newComp.setDividendDate(dividendDate);

        return newComp;
    }
}
