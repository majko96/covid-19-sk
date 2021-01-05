package com.precin.covid_19_sk.model;


public class Towns {

    private String town;
    private Integer infectedTown;
    private Integer todayNew;

    public Towns(String town, Integer infectedTown, Integer todayNew) {
        this.town = town;
        this.infectedTown = infectedTown;
        this.todayNew = todayNew;
    }

    public String getTown() {
        return town;
    }
    public Integer getInfectedTown() {
        return infectedTown;
    }
    public Integer getTodayNew(){ return todayNew;}

}
