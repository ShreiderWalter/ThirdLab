package com.example.walter.thirdlab.core;

/**
 * Created by walter on 17.10.14.
 */
public class PlanetItem {

    private String name;
    private Integer currentRate, id;

    public PlanetItem(){}

    public PlanetItem(String name, Integer rate){
        this.name = name;
        this.currentRate = rate;
    }

    public PlanetItem(Integer id, String name, Integer rate){
        this.name = name;
        this.currentRate = rate;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(Integer currentRate) {
        this.currentRate = currentRate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
