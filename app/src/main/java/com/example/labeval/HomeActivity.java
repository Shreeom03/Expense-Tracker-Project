package com.example.labeval;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private TextView totalExpenseTextView, foodExpenseTextView, transportExpenseTextView, accommodationExpenseTextView;
    private Button addExpenseButton;
    private List<Expense> expensesList = new ArrayList<>();  // In-memory list of expenses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        totalExpenseTextView = findViewById(R.id.total_expense);
        foodExpenseTextView = findViewById(R.id.food_expense);
        transportExpenseTextView = findViewById(R.id.transport_expense);
        accommodationExpenseTextView = findViewById(R.id.accommodation_expense);
        addExpenseButton = findViewById(R.id.add_expense_button);

        // Sample data (this should ideally come from the database or other source)
        expensesList.add(new Expense("Lunch", 10.0, "Food", new java.util.Date()));
        expensesList.add(new Expense("Bus", 5.0, "Transport", new java.util.Date()));
        expensesList.add(new Expense("Hotel", 50.0, "Accommodation", new java.util.Date()));

        // Populate the UI with total expenses and category breakdown
        updateUI();

        // Set listener for Add Expense button
        addExpenseButton.setOnClickListener(v -> {
            // Launch AddExpenseActivity (you will need to add this to your manifest as well)
            // For now, we show a toast message
            Toast.makeText(HomeActivity.this, "Add Expense clicked!", Toast.LENGTH_SHORT).show();
        });
    }

    // Method to update the UI with total and category-wise breakdown
    @SuppressLint("NewApi")
    private void updateUI() {
        double totalExpense = 0.0;
        Map<String, Double> categoryExpenses = new HashMap<>();

        // Loop through all expenses and calculate the total and category-wise expenses
        for (Expense expense : expensesList) {
            totalExpense += expense.getAmount();

            // Group by category
            if (categoryExpenses.containsKey(expense.getCategory())) {
                categoryExpenses.put(expense.getCategory(), categoryExpenses.get(expense.getCategory()) + expense.getAmount());
            } else {
                categoryExpenses.put(expense.getCategory(), expense.getAmount());
            }
        }

        // Set total expenses
        totalExpenseTextView.setText("Total Expenses: $" + totalExpense);

        // Set category-wise expenses
        foodExpenseTextView.setText("Food: $" + categoryExpenses.getOrDefault("Food", 0.0));
        transportExpenseTextView.setText("Transport: $" + categoryExpenses.getOrDefault("Transport", 0.0));
        accommodationExpenseTextView.setText("Accommodation: $" + categoryExpenses.getOrDefault("Accommodation", 0.0));
    }
}
