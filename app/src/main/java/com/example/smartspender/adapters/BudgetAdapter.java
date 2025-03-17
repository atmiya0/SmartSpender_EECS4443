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
import com.example.smartspender.model.Budget;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

    private List<Budget> budgetsList = new ArrayList<>();

    public BudgetAdapter(List<Budget> budgetsList) {
        this.budgetsList = budgetsList;
    }
    public BudgetAdapter() {
        this.budgetsList = budgetsList;
    }

    public void SetBudget(List<Budget> budgetsList) {
        this.budgetsList = budgetsList;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgetsList.get(position);
        holder.budget_name.setText(budget.getBudget_name());
        holder.budget_category_and_date.setText(budget.getBudget_category_and_date());
        holder.budget_limit.setText("$" + String.format("%.2f", budget.getBudget_limit()));
        Log.d("RecyclerView", "Binding Budget: " + budget.getBudget_name());
    }

    @Override
    public int getItemCount() {
        return budgetsList.size();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView budget_name, budget_category_and_date, budget_limit;
        CardView cardView;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            budget_name = itemView.findViewById(R.id.list_name);
            budget_category_and_date = itemView.findViewById(R.id.list_details);
            budget_limit = itemView.findViewById(R.id.list_amount);
        }
    }
}