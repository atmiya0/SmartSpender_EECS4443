package com.example.smartspender.ui.budgets;

import com.example.smartspender.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.smartspender.databinding.FragmentBudgetsBinding;

public class BudgetsFragment extends Fragment {

    private FragmentBudgetsBinding binding;
    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private List<Transaction> transactionList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BudgetsViewModel budgetsViewModel =
                new ViewModelProvider(this).get(BudgetsViewModel.class);

        binding = FragmentBudgetsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.summaryTit;
        budgetsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.expenses_recyclerView);

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction("Category 1", "Today", "200"));
        transactionList.add(new Transaction("Category 2", "Yestarday", "0"));
        //Can add more if needed

        // Initialize Adapter and set it to RecyclerView
        transactionAdapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(transactionAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}