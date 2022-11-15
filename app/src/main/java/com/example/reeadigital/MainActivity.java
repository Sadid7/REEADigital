package com.example.reeadigital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);
        //setAppbarConfigWithDestinations();
        //NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);
    }

/*    private void setAppbarConfigWithDestinations() {

        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.mapScreen);
        topLevelDestinations.add(R.id.listScreen);
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations)
                .setDrawerLayout(new DrawerLayout(this))
                .build();
    }*/

}