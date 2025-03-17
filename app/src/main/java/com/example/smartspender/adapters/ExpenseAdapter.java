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
import com.example.smartspender.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expensesList = new ArrayList<>();

    public ExpenseAdapter(List<Expense> expensesList) {
        this.expensesList = expensesList;
    }
    public ExpenseAdapter() {
        this.expensesList = new ArrayList<>();
    }

    public void SetExpense(List<Expense> expensesList) {
        this.expensesList = expensesList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expensesList.get(position);
        holder.expense_type.setText(expense.getExpense_type());
        holder.expense_amount.setText("$" + String.format("%.2f", expense.getExpense_amount()));
        holder.expense_date.setText(expense.getExpense_date());
        Log.d("RecyclerView", "Binding Expense: " + expense.getExpense_type());
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expense_type, expense_amount, expense_date;
        CardView cardView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_type = itemView.findViewById(R.id.list_name);
            expense_date = itemView.findViewById(R.id.list_details);
            expense_amount = itemView.findViewById(R.id.list_amount);
        }
    }
}