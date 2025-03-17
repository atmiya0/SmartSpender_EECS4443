package com.example.smartspender.ui.expenses;

import com.example.smartspender.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Calendar;

import com.example.smartspender.adapters.ExpenseAdapter;
import com.example.smartspender.databinding.FragmentExpensesBinding;
import com.example.smartspender.model.Expense;

public class ExpensesFragment extends Fragment {

    private FragmentExpensesBinding binding;
    private ExpenseAdapter adapter;
    EditText input_expense_type, input_expense_amount, input_expense_date;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ExpensesViewModel expensesViewModel = new ViewModelProvider(this).get(ExpensesViewModel.class);

        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.expenseHeading;

        // expensesViewModel.getAllExpenses().observe(getViewLifecycleOwner(), textView::setText);
        // Initialize RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.expenses_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExpenseAdapter();
        recyclerView.setAdapter(adapter);


        //This initializes the date input field and calls showDatePicker() when clicked.
        input_expense_date = binding.inputExpenseDate;
        input_expense_date.setOnClickListener(v -> showDatePicker());


        // Dropdown (AutoCompleteTextView) for selecting budget categories.
        // Shows a dropdown menu when clicked.
        AutoCompleteTextView categoryDropdown = root.findViewById(R.id.input_expense_type);
        String[] categories = {"Food", "Travel", "Entertainment", "Other"};
        ArrayAdapter<String> Categoryadapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryDropdown.setAdapter(Categoryadapter);
        categoryDropdown.setOnClickListener(v -> categoryDropdown.showDropDown());

        //Initializing other input fields
        input_expense_type = binding.inputExpenseType;
        input_expense_amount = binding.inputExpenseAmount;
        Button addExpenseButton = root.findViewById(R.id.add_expense_button);


        //Accepting inputs through the button
        // Creates a new Expense object and inserts it into the database via expensesViewModel.insert(newExpense).
        addExpenseButton.setOnClickListener(v -> {
            try {
                // Input validation
                String category = categoryDropdown.getText().toString();
                if (category.isEmpty()) {
                    categoryDropdown.setError("Please select a category");
                    return;
                }
                
                String amountStr = input_expense_amount.getText().toString();
                if (amountStr.isEmpty()) {
                    input_expense_amount.setError("Please enter an amount");
                    return;
                }
                
                Double amount = Double.parseDouble(amountStr);
                
                String date = input_expense_date.getText().toString();
                if (date.isEmpty()) {
                    input_expense_date.setError("Please select a date");
                    return;
                }

                // Create and save the expense
                Expense newExpense = new Expense(category, amount, date);
                expensesViewModel.addExpense(newExpense);
                expensesViewModel.insert(newExpense);
                
                // Clear input fields after successful addition
                categoryDropdown.setText("");
                input_expense_amount.setText("");
                input_expense_date.setText("");
                
                Log.d("AddExpense", "Expense added successfully: " + category + ", $" + amount);
            } catch (NumberFormatException e) {
                input_expense_amount.setError("Please enter a valid number");
                Log.e("AddExpense", "Error parsing amount: " + e.getMessage());
            } catch (Exception e) {
                Log.e("AddExpense", "Error adding expense: " + e.getMessage());
            }
        });

        // Observes changes in the expenses list using LiveData.
        // Updates the RecyclerView adapter whenever a new expense is added.
        expensesViewModel.getExpenses().observe(getViewLifecycleOwner(), expenses -> {
            adapter.SetExpense(expenses);
            adapter.notifyDataSetChanged();
            
            // Calculate total expenses
            double totalExpenses = 0.0;
            for (Expense expense : expenses) {
                totalExpenses += expense.getExpense_amount();
            }
            
            // Format the total expenses with currency symbol and styling
            String formattedTotal = String.format("$%.0f", totalExpenses);
            
            // Create a SpannableString to style the amount in red
            SpannableString spannableString = new SpannableString(formattedTotal);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.expense_red)), 
                0, formattedTotal.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            // Update the expenseValue TextView with the formatted total
            TextView expenseValueTextView = binding.expenseValue;
            expenseValueTextView.setText(spannableString);
        });
        return root;
    }


    // Displays a DatePickerDialog, allowing the user to select a date for the budget.
    // Formats the selected date as DD/MM/YYYY and sets it in etDate.
    private void showDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(requireContext(), (view1, selectedYear, selectedMonth, selectedDay) -> {
            input_expense_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePicker.show();
    }


    // Prevents memory leaks by setting binding to null when the fragment is destroyed.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}