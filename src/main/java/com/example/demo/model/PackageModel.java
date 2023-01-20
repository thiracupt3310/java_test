package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

public class PackageModel {
    private String id;
    private String name;
    private double price_per_unit;
    private Date create_date;

    public PackageModel(String name, double price_per_unit, Date create_date) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price_per_unit = price_per_unit;
        this.create_date = create_date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice_per_unit(double price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice_per_unit() {
        return price_per_unit;
    }

    public Date getCreate_date() {
        return create_date;
    }
}
