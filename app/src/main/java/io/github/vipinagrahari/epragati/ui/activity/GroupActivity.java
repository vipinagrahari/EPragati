package io.github.vipinagrahari.epragati.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.ui.adapter.FragmentAdapter;
import io.github.vipinagrahari.epragati.ui.fragment.GroupFinanceFragment;
import io.github.vipinagrahari.epragati.ui.fragment.GroupInfoFragment;
import io.github.vipinagrahari.epragati.ui.fragment.GroupMeetingsFragment;

public class GroupActivity extends AppCompatActivity {

    final int TAB_COUNT = 3;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        viewPager = (ViewPager) findViewById(R.id.group_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), TAB_COUNT);
        fragmentAdapter.setItem(0, Fragment.instantiate(GroupActivity.this, GroupInfoFragment.class.getName()), getString(R.string.fragment_group_info));
        fragmentAdapter.setItem(1, Fragment.instantiate(GroupActivity.this, GroupMeetingsFragment.class.getName()), getString(R.string.fragment_group_meetings));
        fragmentAdapter.setItem(2, Fragment.instantiate(GroupActivity.this, GroupFinanceFragment.class.getName()), getString(R.string.fragment_group_finance));
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
