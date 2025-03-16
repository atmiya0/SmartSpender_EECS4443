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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;
import com.example.smartspender.adapters.TransactionAdapter;
import com.example.smartspender.model.Transaction;
import android.app.DatePickerDialog;
import android.widget.EditText;
import java.util.Calendar;

import com.example.smartspender.databinding.FragmentBudgetsBinding;

public class BudgetsFragment extends Fragment {

    private FragmentBudgetsBinding binding;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList;
    EditText etDate, nameInput, limitInput;
    private Button createBudgetButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BudgetsViewModel budgetsViewModel =
                new ViewModelProvider(this).get(BudgetsViewModel.class);

        binding = FragmentBudgetsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.summaryTit;
//        budgetsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.budgets_recyclerView);

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        transactionList = new ArrayList<>();
//        transactionList.add(new Transaction("Category 1", "Today", 200.0));
//        transactionList.add(new Transaction("Category 2", "Yestarday", 0.0));
//        //Can add more if needed

        // Initialize Adapter and set it to RecyclerView
        adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        //Initializes the date picker
        etDate = binding.etDate;
        etDate.setOnClickListener(v -> showDatePicker());

        // Initialize the AutoCompleteTextView
        AutoCompleteTextView categoryDropdown = root.findViewById(R.id.category);
        String[] categories = {"Finance", "Food", "Transport", "Shopping", "Rent", "Utilities", "Entertainment"};
        ArrayAdapter<String> Categoryadapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        categoryDropdown.setAdapter(Categoryadapter);
        categoryDropdown.setOnClickListener(v -> categoryDropdown.showDropDown());

        //Initializing other fields
        nameInput = binding.name;
        limitInput = binding.limit;
        createBudgetButton = root.findViewById(R.id.budget_button);

        //Accepting inputs through the button
        createBudgetButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String category = categoryDropdown.getText().toString();
            Double limit = Double.parseDouble(limitInput.getText().toString());
            String date = etDate.getText().toString();
            // Debug output before saving
            Log.d("CreateBudget", "Budget Name: " + name);
            Log.d("CreateBudget", "Category: " + category);
            Log.d("CreateBudget", "Budget Limit: " + limit);
            Log.d("CreateBudget", "Date: " + date);

            Transaction newBudget = new Transaction(name, category+" - "+date, limit);
            budgetsViewModel.addBudget(newBudget);
            budgetsViewModel.insert(newBudget);
            Log.d("CreateBudget", "Button clicked!"); // Debug log
        });
        budgetsViewModel.getBudgets().observe(getViewLifecycleOwner(), budgets -> {
            adapter.SetTransaction(budgets);
            adapter.notifyDataSetChanged();
        });
        return root;
    }

    private void showDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(requireContext(), (view1, selectedYear, selectedMonth, selectedDay) -> {
            etDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePicker.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}