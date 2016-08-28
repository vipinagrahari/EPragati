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
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.ui.adapter.FragmentAdapter;
import io.github.vipinagrahari.epragati.ui.fragment.MyDetailsFragment;
import io.github.vipinagrahari.epragati.ui.fragment.MyDreamsFragment;
import io.smooch.ui.ConversationActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final int TAB_COUNT = 2;
    ViewPager viewPager;
    TabLayout tabLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

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
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


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
                activityToOpen = ConversationActivity.class;
                break;
            default:
                //Stay Here
                break;
        }
        if (null != activityToOpen) {
            logNavigationSelection(activityToOpen.getName());
            Intent intent = new Intent(MainActivity.this, activityToOpen);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    public void logNavigationSelection(String className) {

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, className);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "navigation");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }


}
