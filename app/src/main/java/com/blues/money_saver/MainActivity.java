package com.blues.money_saver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
       mTracker = application.getDefaultTracker();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus);
        fab.setContentDescription(getString(R.string.a11y_fab));
        final Intent intent = new Intent(this,CreateNewMoneyActivity.class);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Utility.setCategory(getString(R.string.nav_overview_str));
        Utility.setTabindex(9);

        transfragment(getString(R.string.nav_overview_str));
        //FragmentManager mFragmentManager;
       // FragmentTransaction mFragmentTransaction;
      //  navigationView.getMenu().getItem(R.id.nav_daily).setChecked(true);
      //  mFragmentManager = getSupportFragmentManager();
     //   mFragmentTransaction = mFragmentManager.beginTransaction();
       // TabFragment tabfragment = new TabFragment();

       // mFragmentTransaction.replace(R.id.content_main_fragment,tabfragment).commit();
//
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

    @Override
    public void onResume()
    {
        super.onResume();


    }

    private void transfragment(String menuid)
    {
        TabFragment tabfragment = new TabFragment();
        Utility.setCategory(menuid);
        FragmentManager mFragmentManager;
        FragmentTransaction mFragmentTransaction;
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        // mFragmentTransaction.
        mFragmentTransaction.replace(R.id.content_main_fragment,tabfragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_overview) {transfragment(getString(R.string.nav_overview_str));
            sendActionName(getString(R.string.nav_overview_str));}
        else if (id == R.id.nav_daily) {transfragment(getString(R.string.nav_daily_str));
            sendActionName(getString(R.string.nav_daily_str));}
        else if (id == R.id.nav_insurance) {transfragment(getString(R.string.nav_insurance_str));
        sendActionName(getString(R.string.nav_insurance_str));}
        else if (id == R.id.nav_utility) {transfragment(getString(R.string.nav_utility_str));
            sendActionName(getString(R.string.nav_utility_str));}
        else if (id == R.id.nav_loan) {transfragment(getString(R.string.nav_loan_str));
        sendActionName(getString(R.string.nav_loan_str));}
        else if (id == R.id.nav_income) {transfragment(getString(R.string.nav_income_str));
            sendActionName(getString(R.string.nav_income_str));
        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void sendActionName(String name) {

        // [START screen_view_hit]
        //Log.i(TAG, "navigation name: " + name);
       // mTracker.setScreenName("navigation: " + name);
        mTracker.send(new HitBuilders.EventBuilder()
        .setCategory("Action")
        .setAction(name)
        .build());
        // [END screen_view_hit]
    }
}
