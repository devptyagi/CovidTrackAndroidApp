package com.devtyagi.covidtrack.model;

public class CountryData {
    String countryName;
    String activeCases;
    String totalCases;
    String recovered;
    String deaths;

    public CountryData(String countryName, String activeCases, String totalCases, String recovered, String deaths) {
        this.countryName = (countryName.isEmpty())?"0":countryName;
        this.activeCases = (activeCases.isEmpty())?"0":activeCases;
        this.totalCases = (totalCases.isEmpty())?"0":totalCases;
        this.recovered = (recovered.isEmpty())?"0":recovered;
        this.deaths = (deaths.isEmpty())?"0":deaths;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getActiveCases() {
        return activeCases;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeaths() {
        return deaths;
    }
}
