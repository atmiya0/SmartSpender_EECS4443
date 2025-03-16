package com.example.smartspender.ui.income;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspender.R;
import com.example.smartspender.adapters.TransactionAdapter;
import com.example.smartspender.databinding.FragmentIncomeBinding;
import com.example.smartspender.model.Transaction;

import java.util.Calendar;
import java.util.Locale;

public class IncomeFragment extends Fragment {

    private FragmentIncomeBinding binding;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private EditText etDate;
    private Button createIncomeButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IncomeViewModel incomeViewModel =
                new ViewModelProvider(this).get(IncomeViewModel.class);

        binding = FragmentIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set summary title text if needed
        final TextView textView = binding.summaryTit;
        // incomeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.income_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        // Initialize the Date EditText and set its click listener
        etDate = binding.etDate;
        etDate.setOnClickListener(v -> showDatePicker());

        // Initialize the AutoCompleteTextView for Income Source
        AutoCompleteTextView incomeSourceDropdown = root.findViewById(R.id.incomeSource);
        String[] sources = {"Work", "Investment", "Tax Return"};
        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, sources);
        incomeSourceDropdown.setAdapter(sourceAdapter);
        incomeSourceDropdown.setOnClickListener(v -> incomeSourceDropdown.showDropDown());

        // Initialize the Amount input field
        EditText amountInput = binding.incomeAmount;

        // Initialize the Add Income button
        createIncomeButton = root.findViewById(R.id.add_income_button);
        createIncomeButton.setOnClickListener(v -> {
            String source = incomeSourceDropdown.getText().toString();
            String amountStr = amountInput.getText().toString();
            if (amountStr.isEmpty()) {
                Log.d("CreateIncome", "Amount input is empty!");
                return;
            }
            double amount = Double.parseDouble(amountStr);
            String date = etDate.getText().toString();

            Log.d("CreateIncome", "Income Source: " + source);
            Log.d("CreateIncome", "Amount: " + amount);
            Log.d("CreateIncome", "Date: " + date);

            // Create a new Transaction (update parameters as needed)
            Transaction newIncome = new Transaction(source, date, amount);
            incomeViewModel.addIncome(newIncome);
            Log.d("CreateIncome", "Button clicked! Income added.");
        });

        incomeViewModel.getIncomes().observe(getViewLifecycleOwner(), incomes -> {
            adapter.SetTransaction(incomes);
            adapter.notifyDataSetChanged();
        });

        return root;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String formattedDate = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d", selectedDay, (selectedMonth + 1), selectedYear);
                    etDate.setText(formattedDate);
                }, year, month, day);

        datePicker.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
