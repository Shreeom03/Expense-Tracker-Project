package com.example.labeval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseManager {

    private static ExpenseManager instance;
    private List<Expense> expenses;

    private ExpenseManager() {
        expenses = new ArrayList<>();
    }

    public static ExpenseManager getInstance() {
        if (instance == null) {
            instance = new ExpenseManager();
        }
        return instance;
    }

    public void addExpense(String name, double amount, String category, Date date) {
        // Check if an expense with the same name and date exists
        for (Expense expense : expenses) {
            if (expense.getName().equals(name) && expense.getDate().equals(date)) {
                // Update existing expense
                expense.setAmount(amount);
                expense.setCategory(category);
                return;
            }
        }
        // If no existing expense found, create a new one
        expenses.add(new Expense(name, amount, category, date));
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }
}
