package com.example.labeval;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView totalExpenseTextView, foodExpenseTextView, transportExpenseTextView, accommodationExpenseTextView, adventureExpenseTextView, extraExpenseTextView;
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
        adventureExpenseTextView = findViewById(R.id.adventure_expense);
        extraExpenseTextView = findViewById(R.id.extra_expense);
        addExpenseButton = findViewById(R.id.add_expense_button);

        // Set up the add expense button
        addExpenseButton.setOnClickListener(v -> {
            // Navigate to AddExpenseActivity when button is clicked
            Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        // Load and display expenses
        loadExpenses();
    }

    private void loadExpenses() {
        List<Expense> expenses = ExpenseManager.getInstance().getAllExpenses();

        double totalExpense = 0;
        double foodExpense = 0;
        double transportExpense = 0;
        double accommodationExpense = 0;
        double adventureExpense = 0;
        double extraExpense = 0;

        // Calculate total and category expenses
        for (Expense expense : expenses) {
            totalExpense += expense.getAmount();
            switch (expense.getCategory()) {
                case "Food":
                    foodExpense += expense.getAmount();
                    break;
                case "Transport":
                    transportExpense += expense.getAmount();
                    break;
                case "Accommodation":
                    accommodationExpense += expense.getAmount();
                    break;
                case "Adventure":
                    adventureExpense += expense.getAmount();
                    break;
                case "Extra":
                    extraExpense += expense.getAmount();
                    break;
            }
        }

        // Update the UI with the totals
        totalExpenseTextView.setText("Total: " + totalExpense);
        foodExpenseTextView.setText("Food: " + foodExpense);
        transportExpenseTextView.setText("Transport: " + transportExpense);
        accommodationExpenseTextView.setText("Accommodation: " + accommodationExpense);
        adventureExpenseTextView.setText("Adventure: " + adventureExpense);
        extraExpenseTextView.setText("Extra: " + extraExpense);
    }
}
