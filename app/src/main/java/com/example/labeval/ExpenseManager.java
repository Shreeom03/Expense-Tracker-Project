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

    // Add new expense
    public void addExpense(String name, double amount, String category, Date date) {
        expenses.add(new Expense(name, amount, category, date));
    }

    // Remove an expense
    public void removeExpense(Expense expense) {
        expenses.remove(expense);
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenses;
    }

    // Get expenses filtered by category
    public List<Expense> getExpensesByCategory(String category) {
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }
}
