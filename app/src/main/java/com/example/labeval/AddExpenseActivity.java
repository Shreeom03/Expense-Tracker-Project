package com.example.labeval;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText, expenseAmountEditText;
    private Spinner expenseCategorySpinner;
    private Button saveExpenseButton, expenseDateButton;
    private String selectedDate = "";
    private List<Expense> expensesList = new ArrayList<>();  // List to hold all expenses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize the views
        expenseNameEditText = findViewById(R.id.expense_name);
        expenseAmountEditText = findViewById(R.id.expense_amount);
        expenseCategorySpinner = findViewById(R.id.expense_category);
        expenseDateButton = findViewById(R.id.expense_date_button);
        saveExpenseButton = findViewById(R.id.save_expense_button);

        // Set up category spinner (this can be populated with dynamic data later)
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.expense_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategorySpinner.setAdapter(categoryAdapter);
    }

    // Method to open DatePickerDialog
    public void onSelectDateClick(View view) {
        // Logic for opening the DatePicker (same as before)
    }

    // Method to save or update the expense when save button is clicked
    public void onSaveExpense(View view) {
        String name = expenseNameEditText.getText().toString().trim();
        String amountText = expenseAmountEditText.getText().toString().trim();
        String category = expenseCategorySpinner.getSelectedItem().toString();

        // Ensure that the amount field is not empty
        if (name.isEmpty() || amountText.isEmpty() || selectedDate.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the amount to double
        double amount = Double.parseDouble(amountText);

        // Create the Expense object
        Expense newExpense = new Expense(name, amount, category, new Date());

        // Check if an expense with the same name and date already exists
        boolean exists = false;
        for (Expense expense : expensesList) {
            if (expense.equals(newExpense)) {
                // If the expense exists, update it
                expense.setAmount(amount);
                expense.setCategory(category);
                exists = true;
                break;
            }
        }

        // If the expense doesn't exist, add it to the list
        if (!exists) {
            expensesList.add(newExpense);
            Toast.makeText(this, "New expense added: " + name + " - $" + amount, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Expense updated: " + name + " - $" + amount, Toast.LENGTH_SHORT).show();
        }

        // Close the current activity and return to HomeActivity
        finish();
    }
}
