package io.github.vipinagrahari.epragati.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.ui.adapter.FragmentAdapter;
import io.github.vipinagrahari.epragati.ui.fragment.ResourcesFragment;


public class ResourcesActivity extends AppCompatActivity {

    final int TAB_COUNT = 2;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        viewPager = (ViewPager) findViewById(R.id.resources_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), TAB_COUNT);
        Bundle bundleEducation = new Bundle();
        bundleEducation.putString("TAG", "EDUCATION");
        Bundle bundleSkills = new Bundle();
        bundleSkills.putString("TAG", "SKILLS");
        fragmentAdapter.setItem(0, Fragment.instantiate(ResourcesActivity.this, ResourcesFragment.class.getName(), bundleEducation), getString(R.string.fragment_resources_education));
        fragmentAdapter.setItem(1, Fragment.instantiate(ResourcesActivity.this, ResourcesFragment.class.getName(), bundleSkills), getString(R.string.fragment_resources_skills));

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}