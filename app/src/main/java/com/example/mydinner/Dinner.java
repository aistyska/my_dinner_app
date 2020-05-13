package com.example.mydinner;

import java.util.ArrayList;
import java.util.List;

public class Dinner {

    private String dishType;
    private String dishName;
    private double price;
    private boolean deliverable;
    private String paymentType;

    public Dinner(String dishType, String dishName, double price, boolean deliverable, String paymentType) {
        this.dishType = dishType;
        this.dishName = dishName;
        this.price = price;
        this.deliverable = deliverable;
        this.paymentType = paymentType;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
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
}
