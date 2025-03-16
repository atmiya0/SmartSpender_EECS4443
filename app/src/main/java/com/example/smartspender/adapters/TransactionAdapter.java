package com.example.smartspender.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspender.R;
import com.example.smartspender.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> budgetsList = new ArrayList<>();

    public TransactionAdapter(List<Transaction> budgetsList) {
        this.budgetsList = budgetsList;
    }
    public TransactionAdapter() {
        this.budgetsList = budgetsList;
    }

    public void SetTransaction(List<Transaction> budgetsList) {
        this.budgetsList = budgetsList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction budget = budgetsList.get(position);
        holder.transactionName.setText(budget.getName());
        holder.transactionDetails.setText(budget.getDetails());
        holder.transactionAmount.setText("$" + String.format("%.2f", budget.getAmount()));
        Log.d("RecyclerView", "Binding Budget: " + budget.getName());
    }

    @Override
    public int getItemCount() {
        return budgetsList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionName, transactionDetails, transactionAmount;
        CardView cardView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionName = itemView.findViewById(R.id.text_transaction_name);
            transactionDetails = itemView.findViewById(R.id.text_transaction_details);
            transactionAmount = itemView.findViewById(R.id.text_transaction_amount);
        }
    }
}