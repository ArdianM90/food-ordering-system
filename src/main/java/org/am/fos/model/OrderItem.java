package org.am.fos.model;

import org.am.fos.collector.CuisineEnum;
import org.am.fos.collector.OrderItemEnum;

public abstract class OrderItem {
    private OrderItemEnum type;
    private CuisineEnum cuisine;
    private String name;
    private double price;

    public OrderItemEnum getType() {
        return type;
    }

    public void setType(OrderItemEnum type) {
        this.type = type;
    }

    public CuisineEnum getCuisine() {
        return cuisine;
    }

    public void setCuisine(CuisineEnum cuisine) {
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
