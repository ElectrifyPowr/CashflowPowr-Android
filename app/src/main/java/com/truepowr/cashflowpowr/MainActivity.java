package com.truepowr.cashflowpowr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final int ID_HOME = 0, ID_HISTORY = 1, ID_STATS = 2, ID_SETTINGS = 3;
    private final int[] theIds = new int[]{ID_HOME, ID_HISTORY, ID_STATS, ID_SETTINGS};
    private Fragment[] allFragments = new Fragment[]{
            new HomeFragment(), new HistoryFragment(), new StatisticsFragment(), new SettingsFragment()
    };
    private int[] fragmentLayouts = new int[]{
            R.id.home_fragment, R.id.history_fragment, R.id.statistics_fragment, R.id.settings_fragment
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        switchFragments(ID_HOME);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            switchFragments(ID_HOME);
        } else if (id == R.id.nav_history) {
            switchFragments(ID_HISTORY);
        } else if (id == R.id.nav_statistics) {
            switchFragments(ID_STATS);
        } else if (id == R.id.nav_settings) {
            switchFragments(ID_SETTINGS);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragments(int id){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        for (int i=0; i<theIds.length; i++){
            if (id==theIds[i]){
                //show selected fragment
                if (fm.findFragmentById(fragmentLayouts[i]) != null) {
                    //if the fragment exists, show it.
                    transaction.show(fm.findFragmentById(fragmentLayouts[i]));
                } else {
                    //if the fragment does not exist, add it to fragment manager.
                    transaction.add(fragmentLayouts[i], allFragments[i]);
                }

                //hide other fragments
                for (int j=0; j<theIds.length; j++){
                    if (j != i){
                        if (fm.findFragmentById(fragmentLayouts[j]) != null) {
                            transaction.hide(fm.findFragmentById(fragmentLayouts[j]));
                        }
                    }
                }
                break;
            }
        }
        transaction.commit();
    }

}
