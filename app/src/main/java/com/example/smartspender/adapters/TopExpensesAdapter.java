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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter for displaying top expenses in the summary screen
 * Sorts expenses by amount in descending order and limits to top 3
 */
public class TopExpensesAdapter extends RecyclerView.Adapter<TopExpensesAdapter.TopExpenseViewHolder> {

    private List<Expense> expensesList = new ArrayList<>();
    private final int MAX_ITEMS = 3; // Limit to top 3 expenses

    public TopExpensesAdapter() {
        this.expensesList = new ArrayList<>();
    }

    /**
     * Updates the adapter with a new list of expenses
     * Sorts them by amount in descending order and limits to top 3
     * @param expenses The full list of expenses
     */
    public void setExpenses(List<Expense> expenses) {
        // Create a copy of the list to avoid modifying the original
        List<Expense> sortedExpenses = new ArrayList<>(expenses);
        
        // Sort expenses by amount in descending order
        Collections.sort(sortedExpenses, new Comparator<Expense>() {
            @Override
            public int compare(Expense e1, Expense e2) {
                // Compare in reverse order for descending sort
                return Double.compare(e2.getExpense_amount(), e1.getExpense_amount());
            }
        });
        
        // Limit to top 3 expenses (or less if there are fewer than 3)
        int itemCount = Math.min(sortedExpenses.size(), MAX_ITEMS);
        this.expensesList = sortedExpenses.subList(0, itemCount);
        
        // Notify that the data has changed
        notifyDataSetChanged();
        
        Log.d("TopExpensesAdapter", "Updated with " + itemCount + " expenses");
    }

    @NonNull
    @Override
    public TopExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TopExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopExpenseViewHolder holder, int position) {
        Expense expense = expensesList.get(position);
        holder.expense_type.setText(expense.getExpense_type());
        holder.expense_amount.setText("$" + String.format("%.2f", expense.getExpense_amount()));
        holder.expense_date.setText(expense.getExpense_date());
        Log.d("TopExpensesAdapter", "Binding expense at position " + position + ": " + expense.getExpense_type() + " - $" + expense.getExpense_amount());
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    static class TopExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView expense_type, expense_amount, expense_date;
        CardView cardView;

        public TopExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_type = itemView.findViewById(R.id.list_name);
            expense_date = itemView.findViewById(R.id.list_details);
            expense_amount = itemView.findViewById(R.id.list_amount);
        }
    }
}
