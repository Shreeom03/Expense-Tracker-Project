package com.example.labeval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseManager {

    // Singleton instance
    private static ExpenseManager instance;

    // List to hold all expenses
    private List<Expense> expensesList;

    // Private constructor to prevent instantiation from outside
    private ExpenseManager() {
        expensesList = new ArrayList<>();
    }

    // Get the singleton instance
    public static ExpenseManager getInstance() {
        if (instance == null) {
            instance = new ExpenseManager();
        }
        return instance;
    }

    // Add a new expense
    public void addExpense(String name, double amount, String category, Date date) {
        expensesList.add(new Expense(name, amount, category, date));
    }

    // Update an existing expense (find by name and date)
    public void updateExpense(String name, Date date, double newAmount) {
        for (Expense expense : expensesList) {
            if (expense.getName().equals(name) && expense.getDate().equals(date)) {
                expense.setAmount(newAmount);
                break;
            }
        }
    }

    // Get all expenses
    public List<Expense> getExpenses() {
        return expensesList;
    }

    // Get category-wise expenses
    public double getCategoryExpense(String category) {
        double total = 0.0;
        for (Expense expense : expensesList) {
            if (expense.getCategory().equals(category)) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    // Get total expenses
    public double getTotalExpense() {
        double total = 0.0;
        for (Expense expense : expensesList) {
            total += expense.getAmount();
        }
        return total;
    }
}
