package com.example.smartspender.ui.income;

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

import com.example.smartspender.adapters.IncomeAdapter;
import com.example.smartspender.databinding.FragmentIncomeBinding;
import com.example.smartspender.model.Income;
import com.example.smartspender.ui.income.IncomeViewModel;

public class IncomeFragment extends Fragment {

    private FragmentIncomeBinding binding;
    private IncomeAdapter adapter;
    EditText input_income_type, input_income_amount, input_income_date;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IncomeViewModel incomeViewModel = new ViewModelProvider(this).get(IncomeViewModel.class);

        binding = FragmentIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.incomeHeading;

        // expensesViewModel.getAllExpenses().observe(getViewLifecycleOwner(), textView::setText);
        // Initialize RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.income_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new IncomeAdapter();
        recyclerView.setAdapter(adapter);


        //This initializes the date input field and calls showDatePicker() when clicked.
        input_income_date = binding.inputIncomeDate;
        input_income_date.setOnClickListener(v -> showDatePicker());


        // Dropdown (AutoCompleteTextView) for selecting budget categories.
        // Shows a dropdown menu when clicked.
        AutoCompleteTextView categoryDropdown = root.findViewById(R.id.input_income_type);
        String[] categories = {"Work", "Freelance", "Cash Job", "Side Hustle"};
        ArrayAdapter<String> Categoryadapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryDropdown.setAdapter(Categoryadapter);
        categoryDropdown.setOnClickListener(v -> categoryDropdown.showDropDown());

        //Initializing other input fields
        input_income_type = binding.inputIncomeType;
        input_income_amount = binding.inputIncomeAmount;
        Button addIncomeButton = root.findViewById(R.id.add_income_button);


        //Accepting inputs through the button
        // Creates a new Income object and inserts it into the database via incomeViewModel.insert(newIncome).
        addIncomeButton.setOnClickListener(v -> {
            try {
                // Input validation
                String category = categoryDropdown.getText().toString();
                if (category.isEmpty()) {
                    categoryDropdown.setError("Please select a category");
                    return;
                }
                
                String amountStr = input_income_amount.getText().toString();
                if (amountStr.isEmpty()) {
                    input_income_amount.setError("Please enter an amount");
                    return;
                }
                
                Double amount = Double.parseDouble(amountStr);
                
                String date = input_income_date.getText().toString();
                if (date.isEmpty()) {
                    input_income_date.setError("Please select a date");
                    return;
                }

                // Create and save the income
                Income newIncome = new Income(category, amount, date);
                incomeViewModel.addIncome(newIncome);
                incomeViewModel.insert(newIncome);
                
                // Clear input fields after successful addition
                categoryDropdown.setText("");
                input_income_amount.setText("");
                input_income_date.setText("");
                
                Log.d("AddIncome", "Income added successfully: " + category + ", $" + amount);
            } catch (NumberFormatException e) {
                input_income_amount.setError("Please enter a valid number");
                Log.e("AddIncome", "Error parsing amount: " + e.getMessage());
            } catch (Exception e) {
                Log.e("AddIncome", "Error adding income: " + e.getMessage());
            }
        });

        // Observes changes in the income list using LiveData.
        // Updates the RecyclerView adapter whenever a new income is added.
        incomeViewModel.getIncomes().observe(getViewLifecycleOwner(), incomes -> {
            adapter.SetIncome(incomes);
            adapter.notifyDataSetChanged();
            
            // Calculate total income
            double totalIncome = 0.0;
            for (Income income : incomes) {
                totalIncome += income.getIncome_amount();
            }
            
            // Format the total income with currency symbol and styling
            String formattedTotal = String.format("$%.0f", totalIncome);
            
            // Create a SpannableString to style the amount in green
            SpannableString spannableString = new SpannableString(formattedTotal);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.income_green)), 
                0, formattedTotal.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
            // Update the incomeValue TextView with the formatted total
            TextView incomeValueTextView = binding.incomeValue;
            incomeValueTextView.setText(spannableString);
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
            input_income_date.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
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