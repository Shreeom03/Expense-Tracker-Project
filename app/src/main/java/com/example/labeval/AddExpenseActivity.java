package com.example.labeval;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText, expenseAmountEditText;
    private Spinner categorySpinner;
    private Button dateButton, saveExpenseButton;
    private CheckBox editCheckBox;

    private String selectedCategory;
    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize views
        expenseNameEditText = findViewById(R.id.expense_name);
        expenseAmountEditText = findViewById(R.id.expense_amount);
        categorySpinner = findViewById(R.id.expense_category);
        dateButton = findViewById(R.id.expense_date_button);
        saveExpenseButton = findViewById(R.id.save_expense_button);
        editCheckBox = findViewById(R.id.edit_checkbox);

        // Set onClick listener for the save button
        saveExpenseButton.setOnClickListener(v -> onSaveExpense());

        // Populate the spinner with categories (You can use an array adapter for simplicity)
        // Example:
        // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // categorySpinner.setAdapter(adapter);
    }

    public void onSelectDateClick(View view) {
        // Implement date picker logic (e.g., using a DatePickerDialog)
    }

    public void onSaveExpense() {
        String expenseName = expenseNameEditText.getText().toString();
        double amount = Double.parseDouble(expenseAmountEditText.getText().toString());
        selectedCategory = categorySpinner.getSelectedItem().toString();

        if (editCheckBox.isChecked()) {
            // Check if an expense with the same name and date exists
            boolean found = false;
            for (Expense expense : ExpenseManager.getInstance().getAllExpenses()) {
                if (expense.getName().equals(expenseName) && expense.getDate().equals(selectedDate)) {
                    // Update existing expense
                    expense.setAmount(amount);
                    expense.setCategory(selectedCategory);
                    found = true;
                    break;
                }
            }

            if (!found) {
                Toast.makeText(this, "Expense not found. Heading back to Dashboard.", Toast.LENGTH_SHORT).show();
                // Navigate to HomeActivity if expense not found
                Intent intent = new Intent(AddExpenseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        } else {
            // Add new expense
            ExpenseManager.getInstance().addExpense(expenseName, amount, selectedCategory, selectedDate);
        }

        // After saving or editing, return to the HomeActivity
        Intent intent = new Intent(AddExpenseActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
