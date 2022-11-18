package com.example.reeadigital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.reeadigital.list.viewmodel.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    private MenuItem mSpinnerItem1 = null;
    //MovieListViewModel movieListViewModel;
    boolean isFirstCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e("sabid too", getResources().getConfiguration().locale.toString());
        setContentView(R.layout.activity_main_layout);
        setupNavigationBar();
        isFirstCall = true;
        //movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
    }
    private void setupNavigationBar(){

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.main, menu);
        mSpinnerItem1 = menu.findItem( R.id.menu_spinner1);
        View view1 = mSpinnerItem1.getActionView();
        if (view1 instanceof Spinner)
        {
            final Spinner spinner = (Spinner) view1;
            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.languages,
                    R.layout.spinner_single_row_item);
            spinner.setAdapter(arrayAdapter);
            Bundle b = getIntent().getExtras();
            if (b != null) {
                int last = (int) getIntent().getExtras().getInt("LastSelectedLanguageCode",0);
                spinner.setSelection(last);
            }

        /*    if (getResources().getConfiguration().locale.toString().equalsIgnoreCase(Utils.ENGLISH_US_LOCALE)) {

            } else if (getResources().getConfiguration().locale.toString().equalsIgnoreCase(Utils.SPANISH_LOCALE)) {
                spinner.setSelection(1);

            }*/
            spinner.setOnItemSelectedListener(this);
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int itemNumber, long l) {
        Log.e("sabid too", getResources().getConfiguration().locale.toString());
        if (!isFirstCall) {
            switch (itemNumber) {
                case 0: {
                    changeLocale(Utils.ENGLISH_US_LOCALE, 0);
                    break;
                }
                case 1: {
                    changeLocale(Utils.SPANISH_LOCALE, 1);
                    break;
                }
            }
        }
        isFirstCall = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void changeLocale(String localeCode, int selectedLanguageId) {
        if (!getResources().getConfiguration().locale.toString().equalsIgnoreCase(localeCode))
        {
            Locale locale = new Locale(localeCode);

            //Locale locale = new Locale("en_US");
            Locale.setDefault(locale);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            Intent restart = new Intent(this, MainActivity.class);
            restart.putExtra("LastSelectedLanguageCode", selectedLanguageId);
            startActivity(restart);
            finish();
        }
    }
}