package com.devtyagi.covidtrack.model;

public class StateData {
    String id;
    String stateName;
    String cases;
    String recovered;
    String deaths;

    public StateData(String id, String stateName, String cases, String recovered, String deaths) {
        this.id = id;
        this.stateName = stateName;
        this.cases = cases;
        this.recovered = recovered;
        this.deaths = deaths;
    }

    public String getId() {
        return id;
    }

    public String getStateName() {
        return stateName;
    }

    public String getCases() {
        return cases;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeaths() {
        return deaths;
    }
}
