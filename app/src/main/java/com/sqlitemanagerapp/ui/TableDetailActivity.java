package com.sqlitemanagerapp.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sqlitemanagerapp.R;
import com.sqlitemanagerapp.database.DFDatabaseHelper;
import com.sqlitemanagerapp.ui.adapters.ViewPagerAdapter;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by ZkHaider on 5/13/15.
 */
public class TableDetailActivity extends AppCompatActivity implements MaterialTabListener {

    public static final String TAG = TableDetailActivity.class.getSimpleName();

    private DFDatabaseHelper mDFDatabasehelper;
    private String mTableName;
    private List<String> mColumnNames;

    // Views
    @InjectView(R.id.materialTabHost)
    MaterialTabHost mMaterialTabHost;
    @InjectView(R.id.pager)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);
        ButterKnife.inject(this);

        initBundle();
        initToolbar();
        initDatabaseHelper();
        initTabs();
        initAdapter();
    }

    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        mTableName = bundle.getString("table_name");
    }

    private void initToolbar() {
        getSupportActionBar().setTitle(mTableName);
    }

    private void initDatabaseHelper() {
        mDFDatabasehelper = DFDatabaseHelper.getInstance(this);
        try {
            mDFDatabasehelper.create();
            mColumnNames = mDFDatabasehelper.getAllColumnNames(mTableName);
            for (String string : mColumnNames)
                Log.d(TAG, string);
        } catch (IOException e) {
            throw new Error("Unable to create database");
        }
    }

    private void initTabs() {
        for (String string : mColumnNames)
            mMaterialTabHost.addTab(mMaterialTabHost.newTab().setText(string).setTabListener(this));
    }

    private void initAdapter() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTableName, mColumnNames);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    mMaterialTabHost.setSelectedNavigationItem(position);
                }
        });
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        mViewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
}
