package org.am.fos.model;

import org.am.fos.collector.CuisineEnum;

import java.text.DecimalFormat;

public class Order {
    private static final DecimalFormat DF = new DecimalFormat("0.00");
    private CuisineEnum cuisine;
    private Lunch lunch = null;
    private Drink drink = null;

    private void addBasicCourse(BasicCourse basicCourse) {
        if (this.lunch == null) {
            this.lunch = new Lunch();
        }
        lunch.setBasciCourse(basicCourse);
    }

    private void addDessert(Dessert dessert) {
        if (this.lunch == null) {
            this.lunch = new Lunch();
        }
        lunch.setDessert(dessert);
    }

    public <T> void addMeal(T meal) {
        if (meal instanceof BasicCourse) {
            addBasicCourse((BasicCourse) meal);
        } else if (meal instanceof Dessert) {
            addDessert((Dessert) meal);
        } else if (meal instanceof Drink) {
            this.drink = (Drink) meal;
        }
    }

    public CuisineEnum getCuisine() {
        return cuisine;
    }

    public void setCuisine(CuisineEnum cuisine) {
        this.cuisine = cuisine;
    }

    public void printSummary() {
        System.out.println("=== Summary of your order ===");
        System.out.println("Choosen cuisine: "+cuisine);
        lunch.printSummary();
        if (drink != null) {
            System.out.println("Drink: "+drink);
        }
        double price = drink != null ? lunch.countValue()+drink.getPrice() : lunch.countValue();
        System.out.println("Total price: "+DF.format(price)+" PLN");
    }

    public Lunch getLunch() {
        return lunch;
    }

    public Drink getDrink() {
        return drink;
    }
}
