package com.example.labeval;

import java.util.Date;

public class Expense {
    private String name;
    private double amount;
    private String category;
    private Date date;

    // Constructor
    public Expense(String name, double amount, String category, Date date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }
}
