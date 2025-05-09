package org.am.fos.model;

import org.am.fos.collector.CuisineEnum;
import org.am.fos.collector.OrderItemEnum;

import java.util.Objects;

public class Drink extends OrderItem {
    public Drink(String name, double price, CuisineEnum cuisine) {
        setType(OrderItemEnum.DRINK);
        setName(name);
        setPrice(price);
        setCuisine(cuisine);
    }

    @Override
    public int hashCode() {
        return (Objects.hashCode(getType())
                +Objects.hash(getName())
                +Objects.hashCode(getPrice())
                +Objects.hashCode(getCuisine()))/4;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Drink object = (Drink) obj;
        return getType().equals(object.getType())
                && getName().equals(object.getName())
                && getPrice() == object.getPrice()
                && getCuisine().equals(object.getCuisine());
    }

    @Override
    public String toString() {
        return getName()+", price: "+getPrice()+" PLN";
    }
}
