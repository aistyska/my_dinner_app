package com.example.mydinner;

import java.util.ArrayList;
import java.util.List;

public class Dinner {

    private boolean soup;
    private boolean mainDish;
    private boolean salad;
    private String dishName;
    private double price;
    private boolean deliverable;
    private String paymentType;

    public Dinner(boolean soup, boolean mainDish, boolean salad, String dishName, double price, boolean deliverable, String paymentType) {
        this.soup = soup;
        this.mainDish = mainDish;
        this.salad = salad;
        this.dishName = dishName;
        this.price = price;
        this.deliverable = deliverable;
        this.paymentType = paymentType;
    }

    public boolean isSoup() {
        return soup;
    }

    public void setSoup(boolean soup) {
        this.soup = soup;
    }

    public boolean isMainDish() {
        return mainDish;
    }

    public void setMainDish(boolean mainDish) {
        this.mainDish = mainDish;
    }

    public boolean isSalad() {
        return salad;
    }

    public void setSalad(boolean salad) {
        this.salad = salad;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDeliverable() {
        return deliverable;
    }

    public void setDeliverable(boolean deliverable) {
        this.deliverable = deliverable;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<String> getDishTypes() {
        List<String> dishTypesArray = new ArrayList<>();
        if (soup) {
            dishTypesArray.add("Sriuba");
        }
        if (mainDish) {
            dishTypesArray.add("Pagrindinis");
        }
        if (salad) {
            dishTypesArray.add("Salotos");
        }
        return dishTypesArray;
    }

}
