package com.example.stylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

//точка входа приложения
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Добавляем меню
        DrawerLayout drawer_layout = findViewById(R.id.drawer_layout);
        findViewById(R.id.menu_button).setOnClickListener(v -> {
            drawer_layout.openDrawer(GravityCompat.START);
        });
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
}