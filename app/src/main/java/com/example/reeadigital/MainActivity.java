package com.example.reeadigital;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    boolean isFirstCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        setupNavigationBar();
        isFirstCall = true;
    }
    private void setupNavigationBar(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_menu, menu);
        MenuItem spinnerMenuItem = menu.findItem(R.id.spinner_view_menu_item);
        View actionView = spinnerMenuItem.getActionView();
        if (actionView instanceof Spinner)
        {
            final Spinner spinner = (Spinner) actionView;
            ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.languages,
                    R.layout.spinner_single_row_item);
            spinner.setAdapter(arrayAdapter);
            setLastSelectedLocaleInSpinner(spinner);
            spinner.setOnItemSelectedListener(this);
        }
        return true;
    }

    private void setLastSelectedLocaleInSpinner(Spinner spinner) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int last = getIntent().getExtras().getInt("LastSelectedLanguageCode",0);
            spinner.setSelection(last);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int itemNumber, long l) {
        if (!isFirstCall) {
            if (itemNumber == Utils.ENGLISH_US_LOCALE_SPINNER_ID) {
                setSelectedLocale(Utils.ENGLISH_US_LOCALE, Utils.ENGLISH_US_LOCALE_SPINNER_ID);
            } else {
                setSelectedLocale(Utils.SPANISH_LOCALE, Utils.SPANISH_LOCALE_SPINNER_ID);
            }
        }
        isFirstCall = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private void setSelectedLocale(String localeCode, int selectedLanguageId) {
        if (!getResources().getConfiguration().locale.toString().equalsIgnoreCase(localeCode))
        {
            Locale locale = new Locale(localeCode);
            Locale.setDefault(locale);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            restartActivity(selectedLanguageId);
        }
    }
    private void restartActivity(int selectedLanguageId) {
        Intent restart = new Intent(this, MainActivity.class);
        restart.putExtra("LastSelectedLanguageCode", selectedLanguageId);
        startActivity(restart);
        finish();
    }
}