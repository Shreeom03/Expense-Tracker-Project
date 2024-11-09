package com.example.labeval;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText expenseNameEditText, amountEditText;
    private Spinner categorySpinner;
    private Button saveButton, dateButton;
    private int year, month, day;
    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize views
        expenseNameEditText = findViewById(R.id.expense_name);
        amountEditText = findViewById(R.id.expense_amount);
        categorySpinner = findViewById(R.id.expense_category);
        saveButton = findViewById(R.id.save_expense_button);
        dateButton = findViewById(R.id.expense_date_button);

        // Set up date selection
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Set onClickListener for Date button to open DatePickerDialog
        dateButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddExpenseActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // Update selected date and button text
                        selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                        dateButton.setText("Date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Set onClickListener for Save Expense button
        saveButton.setOnClickListener(v -> saveExpense());
    }

    // Method to save the expense
    private void saveExpense() {
        String name = expenseNameEditText.getText().toString().trim();
        String amountString = amountEditText.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();

        if (name.isEmpty() || amountString.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountString);

        // Add or update expense using the ExpenseManager singleton
        ExpenseManager.getInstance().addExpense(name, amount, category, selectedDate);

        // Show success message and finish the activity
        Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show();
        finish(); // Close activity and return to previous screen
    }
}
