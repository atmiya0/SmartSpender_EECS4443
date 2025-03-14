package com.example.smartspender.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspender.R;
import com.example.smartspender.adapters.TransactionAdapter;
import com.example.smartspender.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment {

    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private List<Transaction> transactionList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        recyclerView = view.findViewById(R.id.recycler_transactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy Transactions for Summary Screen (Top 5 Expenses)
        transactionList = new ArrayList<>();
        transactionList.add(new Transaction("Apple Watch", "20 March", 2000));
        transactionList.add(new Transaction("Netflix Subscription", "18 March", 15));
        transactionList.add(new Transaction("Gym Membership", "15 March", 50));

        transactionAdapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(transactionAdapter);

        return view;
    }
}