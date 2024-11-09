package com.example.labeval;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
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

        // Set up category spinner (options: Food, Transport, Accommodation, Adventure, Extra)
        String[] categories = {"Food", "Transport", "Accommodation", "Adventure", "Extra"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    public void onSelectDateClick(View view) {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Open DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view1, selectedYear, selectedMonth, selectedDay) -> {
                    // Set selected date on the button
                    selectedDate = new Date(selectedYear - 1900, selectedMonth, selectedDay);
                    dateButton.setText(String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear));
                }, year, month, day);
        datePickerDialog.show();
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
