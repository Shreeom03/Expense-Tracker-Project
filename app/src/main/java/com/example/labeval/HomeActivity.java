package com.example.labeval;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private TextView totalExpenseTextView, foodExpenseTextView, transportExpenseTextView, accommodationExpenseTextView;
    private Button addExpenseButton;

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

        // Populate the UI with total expenses and category breakdown
        updateUI();

        // Set listener for Add Expense button
        addExpenseButton.setOnClickListener(v -> {
            // Example: Add a new expense to the singleton
            ExpenseManager.getInstance().addExpense("New Expense", 25.0, "Food", new Date());
            updateUI();
            Toast.makeText(HomeActivity.this, "Expense added!", Toast.LENGTH_SHORT).show();
        });
    }

    // Method to update the UI with total and category-wise breakdown
    private void updateUI() {
        double totalExpense = ExpenseManager.getInstance().getTotalExpense();
        double foodExpense = ExpenseManager.getInstance().getCategoryExpense("Food");
        double transportExpense = ExpenseManager.getInstance().getCategoryExpense("Transport");
        double accommodationExpense = ExpenseManager.getInstance().getCategoryExpense("Accommodation");

        // Set total expenses
        totalExpenseTextView.setText("Total Expenses: $" + totalExpense);

        // Set category-wise expenses (if a category has no expenses, display 0.0)
        foodExpenseTextView.setText("Food: $" + foodExpense);
        transportExpenseTextView.setText("Transport: $" + transportExpense);
        accommodationExpenseTextView.setText("Accommodation: $" + accommodationExpense);
    }
}
