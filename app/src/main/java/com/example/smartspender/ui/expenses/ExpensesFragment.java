package com.example.smartspender.ui.expenses;

import com.example.smartspender.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.smartspender.adapters.TransactionAdapter;
import com.example.smartspender.databinding.FragmentExpensesBinding;
import com.example.smartspender.model.Transaction;


public class ExpensesFragment extends Fragment {

    private FragmentExpensesBinding binding;
    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private List<Transaction> transactionList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExpensesViewModel expensesViewModel =
                new ViewModelProvider(this).get(ExpensesViewModel.class);

        binding = FragmentExpensesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.summaryTit;
        expensesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.expenses_recyclerView);

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction("Coffee", "Beverages - March 20", "$3"));
        transactionList.add(new Transaction("Tea", "Beverages - March 19", "$2"));

        // More items for testing scrollability of RecyclerView
        transactionList.add(new Transaction("Shoes", "Fashion - March 18", "$80"));
        transactionList.add(new Transaction("Shirt", "Fashion - March 18", "$40"));
        transactionList.add(new Transaction("Pants", "Fashion - March 18", "$80"));

        //Can add more if needed

        // Initialize Adapter and set it to RecyclerView
        transactionAdapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(transactionAdapter);

        EditText dateEditText = binding.input3;
        dateEditText.setFocusable(false); // Prevent manual typing
        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateEditText.setText(formattedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}