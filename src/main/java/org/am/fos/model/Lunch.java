package org.am.fos.model;

public class Lunch {
    private BasicCourse basicCourse = null;
    private Dessert dessert = null;

    public void setBasciCourse(BasicCourse basicCourse) {
        this.basicCourse = basicCourse;
    }

    public void setDessert(Dessert dessert) {
        this.dessert = dessert;
    }

    public void printSummary() {
        if (basicCourse != null) {
            System.out.println("Main course: "+basicCourse);
        }
        if (dessert != null) {
            System.out.println("Dessert: "+dessert);
        }
    }

    public double countValue() {
        double value = 0;
        if (basicCourse != null) {
            value += basicCourse.getPrice();
        }
        if (dessert != null) {
            value += dessert.getPrice();
        }
        return value;
    }

    public BasicCourse getBasicCourse() {
        return basicCourse;
    }

    public Dessert getDessert() {
        return dessert;
    }
}
