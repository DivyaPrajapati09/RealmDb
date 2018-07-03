package com.example.acquaint.realmdemo.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class JsonFruits extends RealmObject{

    @PrimaryKey
    private String fruit;
    private String size;
    private String color;

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
