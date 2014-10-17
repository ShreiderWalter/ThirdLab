package com.example.walter.thirdlab;

/**
 * Created by walter on 10.10.14.
 */
public class Planet {

    private final String planetName;
    private Integer drawableId, mapId, radius;

    public Planet(String name, Integer id, Integer map, Integer radius){
        planetName = name;
        mapId = map;
        drawableId = id;
        this.radius = radius;
    }

    public String getPlanetName() {
        return planetName;
    }

    public Integer getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(Integer drawableId) {
        this.drawableId = drawableId;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
