package io.github.vipinagrahari.epragati.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.JsonObject;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.api.ServiceGenerator;
import io.github.vipinagrahari.epragati.ui.adapter.FragmentAdapter;
import io.github.vipinagrahari.epragati.ui.fragment.MyDetailsFragment;
import io.github.vipinagrahari.epragati.ui.fragment.MyDreamsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final int TAB_COUNT = 2;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), TAB_COUNT);
        fragmentAdapter.setItem(0, Fragment.instantiate(MainActivity.this, MyDetailsFragment.class.getName()), getString(R.string.fragment_my_details));
        fragmentAdapter.setItem(1, Fragment.instantiate(MainActivity.this, MyDreamsFragment.class.getName()), getString(R.string.fragment_my_dreams));

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
//
//        Dream dream=new Dream();
//        dream.setTitle("Join Google");
//        dream.setDescription("Work on amazing things");
//        dream.setDeadline(123456789);
//        dream.setComplete(false);
//
//        getContentResolver().insert(DbContract.DreamEntry.CONTENT_URI,dream.getContentValues());
//
//        Transaction transaction=new Transaction();
//        transaction.setDescription("Buy Laptop");
//        transaction.setAmount(30000);
//        transaction.setDate(123456789);
//        transaction.setType("expense");
//        getContentResolver().insert(DbContract.TransactionEntry.CONTENT_URI,transaction.getContentValues());
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
        Class activityToOpen = null;

        switch (id) {
            case R.id.nav_profile:
                //Stay Here
                break;
            case R.id.nav_group:
                activityToOpen = GroupActivity.class;
                break;
            case R.id.nav_finance:
                activityToOpen = FinancialActivity.class;
                break;
            case R.id.nav_resources:
                activityToOpen = ResourcesActivity.class;
                break;
            case R.id.nav_help:
                activityToOpen = HelpActivity.class;
                break;
            default:
                //Stay Here
                break;
        }
        if (activityToOpen != null) {

            Intent intent = new Intent(MainActivity.this, activityToOpen);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadData() {

        Call<JsonObject> getMemberDetails = ServiceGenerator.getInstance().
                getMemberDetails();

        getMemberDetails.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println(response.body().toString());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {


            }
        });

    }
}
