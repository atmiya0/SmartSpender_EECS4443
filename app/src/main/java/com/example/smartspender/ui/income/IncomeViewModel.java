package com.example.smartspender.ui.income;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.smartspender.dao.IncomeDao;
import com.example.smartspender.database.IncomeDatabase;
import com.example.smartspender.model.Income;

import java.util.ArrayList;
import java.util.List;

// Provides a data repository for BudgetsFragment
public class IncomeViewModel extends AndroidViewModel {

    // LiveData that UI fragments observe (BudgetsFragment uses this to update RecyclerView).
    // Whenever budgetsLiveData changes, the UI updates automatically.
    private final MutableLiveData<List<Income>> incomes = new MutableLiveData<>(new ArrayList<>());

    // LiveData that provides a list of all budgets in the database.
    private final LiveData<List<Income>> allIncomes;

    // ExecutorService allows us to run database operations (insert, delete)
    private final ExecutorService executorService;

    // BudgetDao provides methods for interacting with the database.
    private final IncomeDao incomeDao;


    // Initializes the repository and sets up the ExecutorService.
    public IncomeViewModel(Application application) {
        super(application);
        IncomeDatabase db = IncomeDatabase.getInstance(application);
        incomeDao = db.incomeDao();
        allIncomes = incomeDao.getAllIncomes();
        executorService = Executors.newSingleThreadExecutor();
    }

    // Returns the LiveData that UI fragments observe.
    public LiveData<List<Income>> getIncomes() {
        return incomes;
    }

    // Adds a Budget to the list of budgets stored in this ViewModel.
    public void addIncome(Income income) {
        List<Income> currentList = incomes.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>(); // Initialize list if null
        }
        Log.d("CreateIncome", "Inserting income into recyclerview");
        currentList.add(income);
        incomes.setValue(currentList);
    }

    // Returns LiveData that provides a list of all budgets in the database.
    public LiveData<List<Income>> getAllIncomes() {
        return allIncomes;
    }

    //Inserts a Budget into the database
    public void insert(Income income) {
        executorService.execute(() -> incomeDao.insert(income));
    }

    //Deletes a Budget from the database
    public void delete(Income income) {
        executorService.execute(() -> incomeDao.delete(income));
    }

}