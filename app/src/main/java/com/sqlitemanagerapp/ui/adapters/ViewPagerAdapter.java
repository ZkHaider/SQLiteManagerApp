package com.sqlitemanagerapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sqlitemanagerapp.ui.TableDetailFragment;

import java.util.List;

/**
 * Created by ZkHaider on 5/13/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String mTableName;
    private List<String> mColumnNames;

    public ViewPagerAdapter(FragmentManager fm, String tableName, List<String> columnNames) {
        super(fm);
        this.mTableName = tableName;
        this.mColumnNames = columnNames;
    }

    @Override
    public Fragment getItem(int position) {
        TableDetailFragment tableDetailFragment = new TableDetailFragment();
        tableDetailFragment.setArguments(mTableName, mColumnNames.get(position));
        return tableDetailFragment;
    }

    @Override
    public int getCount() {
        return mColumnNames.size();
    }
}
