package com.example.smartspender.ui.budgets;

import com.example.smartspender.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.smartspender.adapters.BudgetAdapter;
import com.example.smartspender.model.Budget;

import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import com.example.smartspender.databinding.FragmentBudgetsBinding;

public class BudgetsFragment extends Fragment {

    private FragmentBudgetsBinding binding;
    private BudgetAdapter adapter;
    EditText input_budget_date, input_budget_name, input_budget_limit;

    // 	This method inflates the fragment layout, initializes UI elements, and sets up ViewModel, RecyclerView, and event listeners.
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Retrieves an instance of BudgetsViewModel, which handles budget data and database interactions.
        BudgetsViewModel budgetsViewModel = new ViewModelProvider(this).get(BudgetsViewModel.class);

        // Uses View Binding (FragmentBudgetsBinding) instead of findViewById(), making UI references safer and easier to manage.
        binding = FragmentBudgetsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // References a TextView (summaryTit), displaying a title or summary.
         final TextView textView = binding.budgetHeading;

        // Initialize RecyclerView —
        // Sets up BudgetAdapter to handle displaying budget data.
        // Uses LinearLayoutManager to display budgets vertically.
        RecyclerView recyclerView = root.findViewById(R.id.budgets_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BudgetAdapter();
        recyclerView.setAdapter(adapter);

        //This initializes the date input field and calls showDatePicker() when clicked.
        input_budget_date = binding.inputBudgetDate;
        input_budget_date.setOnClickListener(v -> showDatePicker());


        // Dropdown (AutoCompleteTextView) for selecting budget categories.
        // Shows a dropdown menu when clicked.
        AutoCompleteTextView categoryDropdown = root.findViewById(R.id.input_budget_category);
        String[] categories = {"Finance", "Food", "Transport", "Shopping", "Rent", "Utilities", "Entertainment"};
        ArrayAdapter<String> CategoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryDropdown.setAdapter(CategoryAdapter);
        categoryDropdown.setOnClickListener(v -> categoryDropdown.showDropDown());

        //Initializing other input fields
        input_budget_name = binding.inputBudgetName;
        input_budget_limit = binding.inputBudgetLimit;
        Button createBudgetButton = root.findViewById(R.id.create_budget_button);


        //Accepting inputs through the button
        // Creates a new Budget object and inserts it into the database via budgetsViewModel.insert(newBudget).
        createBudgetButton.setOnClickListener(v -> {
            String name = input_budget_name.getText().toString();
            String category = categoryDropdown.getText().toString();
            double limit = Double.parseDouble(input_budget_limit.getText().toString());
            String date = input_budget_date.getText().toString();

            Budget newBudget = new Budget(name, category+" - "+date, limit);
            budgetsViewModel.addBudget(newBudget);
            budgetsViewModel.insert(newBudget);
            Log.d("CreateBudget", "Button clicked!"); // Debug log
        });

        // Observes changes in the budget list using LiveData.
        // Updates the RecyclerView adapter whenever a new budget is added.
        budgetsViewModel.getBudgets().observe(getViewLifecycleOwner(), budgets -> {
            adapter.SetBudget(budgets);
            adapter.notifyDataSetChanged();
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
            input_budget_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
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