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

import com.example.smartspender.databinding.FragmentBudgetsBinding;
import com.example.smartspender.expenseItem;

public class BudgetsFragment extends Fragment {

    private FragmentBudgetsBinding binding;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<expenseItem> itemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BudgetsViewModel budgetsViewModel =
                new ViewModelProvider(this).get(BudgetsViewModel.class);

        binding = FragmentBudgetsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.summaryTit;
        budgetsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.budget_recyclerView);

        // Set LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemList = new ArrayList<>();
        itemList.add(new expenseItem("Category 1", 200, "Today"));
        itemList.add(new expenseItem("Category 2", 0, "Yestarday"));
        //Can add more if needed

        // Initialize Adapter and set it to RecyclerView
        recyclerViewAdapter = new RecyclerViewAdapter(itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        return root;
    }

    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private List<expenseItem> itemList;

        // Constructor
        public RecyclerViewAdapter(List<expenseItem> itemList) {
            this.itemList = itemList;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);  // Inflate your item layout
            return new ViewHolder(itemView);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            expenseItem currentItem = itemList.get(position);
            holder.itemView1.setText(currentItem.getCategory());  // Set your data here
            holder.itemView2.setText(currentItem.getDate());
            holder.itemView3.setText(String.valueOf(currentItem.getAmount()));
        }

        // Return the size of the dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return itemList.size();
        }

        // Provide a reference to each item in the view
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView itemView1, itemView2, itemView3;  // Declare your views

            public ViewHolder(View itemView) {
                super(itemView);
                itemView1 = itemView.findViewById(R.id.itemView1);  // Bind views to IDs
                itemView2 = itemView.findViewById(R.id.itemView2);
                itemView3 = itemView.findViewById(R.id.itemView3);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}