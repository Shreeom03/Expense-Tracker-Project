package com.example.labeval;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private TextView totalExpensesText;
    private LinearLayout expenseCategoriesLayout;
    private Button addExpenseButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        totalExpensesText = findViewById(R.id.total_expenses);
        expenseCategoriesLayout = findViewById(R.id.expense_categories);
        addExpenseButton = findViewById(R.id.add_expense_button);

        // Load expenses from Firestore
        loadExpenses();
    }

    private void loadExpenses() {
        CollectionReference expensesRef = db.collection("expenses");

        // Retrieve all expenses
        expensesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Data is fetched from Firestore
                QuerySnapshot querySnapshot = task.getResult();
                double totalExpenses = 0;
                Map<String, Double> categoryTotals = new HashMap<>();

                // Loop through each document (expense)
                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                    String category = document.getString("category");
                    double amount = document.getDouble("amount");

                    // Add to total expenses
                    totalExpenses += amount;

                    // Add amount to category totals
                    categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
                }

                // Update total expenses on the UI
                totalExpensesText.setText("Total Expenses: $" + totalExpenses);

                // Display category breakdown
                displayCategoryBreakdown(categoryTotals);
            } else {
                Toast.makeText(HomeActivity.this, "Error getting expenses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayCategoryBreakdown(Map<String, Double> categoryTotals) {
        expenseCategoriesLayout.removeAllViews();  // Clear previous data

        // Loop through categories and add to layout
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double total = entry.getValue();

            // Create a TextView for each category
            TextView categoryTextView = new TextView(this);
            categoryTextView.setText(category + ": $" + total);
            categoryTextView.setTextSize(18);
            categoryTextView.setPadding(0, 8, 0, 8);

            // Add TextView to the layout
            expenseCategoriesLayout.addView(categoryTextView);
        }
    }

    // OnClick method for Add Expense button
    public void onAddExpenseClick(View view) {
        // Navigate to Add Expense Activity (assuming you have an AddExpenseActivity)
        Intent intent = new Intent(HomeActivity.this, AddExpenseActivity.class);
        startActivity(intent);
    }
}
