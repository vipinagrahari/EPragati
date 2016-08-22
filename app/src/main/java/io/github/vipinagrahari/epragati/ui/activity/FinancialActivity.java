package io.github.vipinagrahari.epragati.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.github.vipinagrahari.epragati.R;
import io.github.vipinagrahari.epragati.ui.adapter.FragmentAdapter;
import io.github.vipinagrahari.epragati.ui.fragment.BudgetFragment;
import io.github.vipinagrahari.epragati.ui.fragment.LoanFragment;

public class FinancialActivity extends AppCompatActivity {

    final int TAB_COUNT = 2;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
        viewPager = (ViewPager) findViewById(R.id.financial_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), TAB_COUNT);
        fragmentAdapter.setItem(0, Fragment.instantiate(FinancialActivity.this, BudgetFragment.class.getName()), getString(R.string.fragment_financial_budget));
        fragmentAdapter.setItem(1, Fragment.instantiate(FinancialActivity.this, LoanFragment.class.getName()), getString(R.string.fragment_financial_loan));

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
