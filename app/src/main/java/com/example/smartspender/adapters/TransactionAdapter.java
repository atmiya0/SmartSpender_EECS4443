package com.example.smartspender.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartspender.R;
import com.example.smartspender.model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.transactionName.setText(transaction.getName());
        holder.transactionDetails.setText(transaction.getDetails());
        holder.transactionAmount.setText(transaction.getAmount());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
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