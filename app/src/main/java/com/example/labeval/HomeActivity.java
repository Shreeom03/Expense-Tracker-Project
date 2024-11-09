package com.example.labeval;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private TextView totalExpensesText;
    private LinearLayout expenseCategoriesLayout;
    private Button addExpenseButton;

    private ArrayList<Expense> expenses = new ArrayList<>();  // List to hold expense objects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        totalExpensesText = findViewById(R.id.total_expenses);
        expenseCategoriesLayout = findViewById(R.id.expense_categories);
        addExpenseButton = findViewById(R.id.add_expense_button);

        // Add sample expenses (in place of Firebase data)
        addSampleExpenses();

        // Load and display expenses
        loadExpenses();
    }

    private void addSampleExpenses() {
        expenses.add(new Expense("Lunch", 50.0, "Food", new Date()));
        expenses.add(new Expense("Taxi", 30.0, "Transport", new Date()));
        expenses.add(new Expense("Hotel", 200.0, "Accommodation", new Date()));
    }

    private void loadExpenses() {
        // Calculate total expenses and category breakdown
        double totalExpenses = 0;
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            double amount = expense.getAmount();
            totalExpenses += amount;

            // Update category breakdown
            String category = expense.getCategory();
            categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
        }

        // Update total expenses text
        totalExpensesText.setText("Total Expenses: $" + totalExpenses);

        // Display category breakdown
        displayCategoryBreakdown(categoryTotals);
    }

    private void displayCategoryBreakdown(Map<String, Double> categoryTotals) {
        expenseCategoriesLayout.removeAllViews();  // Clear previous data

        // Loop through categories and add them to the UI
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double total = entry.getValue();

            TextView categoryTextView = new TextView(this);
            categoryTextView.setText(category + ": $" + total);
            categoryTextView.setTextSize(18);
            categoryTextView.setPadding(0, 8, 0, 8);

            // Add to layout
            expenseCategoriesLayout.addView(categoryTextView);
        }
    }

    // OnClick method for Add Expense button
    public void onAddExpenseClick(View view) {
        // Open Add Expense screen (assuming you have this activity set up)
        // You can pass the list of expenses if needed or handle adding directly
        Toast.makeText(this, "Navigate to Add Expense Screen", Toast.LENGTH_SHORT).show();
    }
}
