package com.kaushal.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    View view;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.design_navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        view = findViewById(R.id.activity_home);

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }//End of onCreateMethod

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_popup, menu);
        return true;
    }//End of onCreateOptionsMenuMethod

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.id_setting) {
//            Toast.makeText(Home.this, "You Have Select Setting in Pop Menu", Toast.LENGTH_LONG).show();
            Snackbar.make(view, "YOu Have Select Setting in Pop Menu", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.id_edit) {
//            Toast.makeText(Home.this, "You Have Select Edit Profile in Pop Menu", Toast.LENGTH_LONG).show();
            Snackbar.make(view, "YOu Have Select Edit Profile in Pop Menu", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.id_sample) {
//            Toast.makeText(Home.this, "You Have Select Sample in Pop Menu", Toast.LENGTH_SHORT).show();
            Snackbar.make(view, "YOu Have Select Sample in Pop Menu", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.search) {
//            Toast.makeText(getApplicationContext(), "Ghanto", Toast.LENGTH_SHORT).show();
            Snackbar.make(view, "YOu Have Select Ghanto in Pop Menu", Snackbar.LENGTH_LONG).show();
        } else if (id == android.R.id.home) {
            startActivity(new Intent(Home.this, SplashScreen.class));
        }
        return super.onOptionsItemSelected(item);
    }//End of onOptionsItemSelectedMethod

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.id_setting) {
//            Toast.makeText(Home.this, "You Have Select Setting in Drawer Menu", Toast.LENGTH_LONG).show();
            Snackbar.make(view, "YOu Have Select Setting in Drawer Menu", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.id_edit) {
//            Toast.makeText(Home.this, "You Have Select Edit Profile in Drawer Menu", Toast.LENGTH_LONG).show();
            Snackbar.make(view, "YOu Have Select Edit Profile in Drawer Menu", Snackbar.LENGTH_LONG).show();
        } else if (id == R.id.id_sample) {
            Toast.makeText(Home.this, "You Have Select Sample in Drawer Menu", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.search) {
//            Toast.makeText(getApplicationContext(), "Ghanto", Toast.LENGTH_SHORT).show();
            Snackbar.make(view, "YOu Have Select Ghanto in Drawer Menu", Snackbar.LENGTH_LONG).show();
        } else if (id == android.R.id.home) {
            startActivity(new Intent(Home.this, SplashScreen.class));
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}//End of Home Class