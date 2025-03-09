package com.example.smartspender.ui.income;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartspender.R;
import com.example.smartspender.databinding.FragmentIncomeBinding;

import java.util.Calendar;
import java.util.Locale;

public class IncomeFragment extends Fragment {

    private FragmentIncomeBinding binding;
    private Button dateButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IncomeViewModel incomeViewModel =
                new ViewModelProvider(this).get(IncomeViewModel.class);

        binding = FragmentIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Example observer for an existing TextView from binding (if needed)
        final TextView textView = binding.sectionTit;
        incomeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Assuming you have added a Button with id "dateButton" in your layout XML
        dateButton = binding.dateButton;
        dateButton.setText("Pick Date");

        // Set a click listener to open the DatePickerDialog
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        return root;
    }

    private void showDatePickerDialog() {
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Format the date as DD/MM/YYYY and set it on the button
                        String formattedDate = String.format(Locale.getDefault(),
                                "%02d/%02d/%04d", selectedDay, (selectedMonth + 1), selectedYear);
                        dateButton.setText(formattedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
