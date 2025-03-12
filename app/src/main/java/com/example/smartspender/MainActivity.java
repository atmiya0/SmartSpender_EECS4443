package com.example.smartspender;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.smartspender.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_summary, R.id.navigation_income, R.id.navigation_expenses,
                R.id.navigation_budgets)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Hide Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        MyDBHelper dbHelper = new MyDBHelper(this);

//        ArrayList<ModalContact> data = dbHelper.fetchContact();
//
//        for(int i = 0; i < data.size(); i++ )
//            Log.d("Contact Info: ", "Name- " + data.get(i).name + " Phone Number- " +
//                    data.get(i).phone_no);

        dbHelper.addBudget("Atmiya", "Finance");


        ModalContact modal = new ModalContact();
        modal.budgets_key_id = 1;
        modal.budgets_name = "Justin";
        modal.budgets_category = "Personal Budget";
        dbHelper.updateContact(modal);

    }
}