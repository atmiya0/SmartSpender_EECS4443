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
import com.example.smartspender.model.Income;

import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private List<Income> incomesList = new ArrayList<>();

    public IncomeAdapter(List<Income> incomesList) {
        this.incomesList = incomesList;
    }
    public IncomeAdapter() {
        this.incomesList = new ArrayList<>();
    }

    public void SetIncome(List<Income> incomesList) {
        this.incomesList = incomesList;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        Income income = incomesList.get(position);
        holder.income_type.setText(income.getIncome_type());
        holder.income_amount.setText(String.valueOf(income.getIncome_amount()));
        holder.income_date.setText(income.getIncome_date());
        Log.d("RecyclerView", "Binding Income: " + income.getIncome_type());
    }

    @Override
    public int getItemCount() {
        return incomesList.size();
    }

    static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView income_type, income_amount, income_date;
        CardView cardView;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            income_type = itemView.findViewById(R.id.list_name);
            income_amount = itemView.findViewById(R.id.list_details);
            income_date = itemView.findViewById(R.id.list_amount);
        }
    }
}