package com.sqlitemanagerapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sqlitemanagerapp.R;
import com.sqlitemanagerapp.database.DFDatabaseHelper;
import com.sqlitemanagerapp.ui.adapters.FragmentDetailAdapter;

import java.io.IOException;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by ZkHaider on 5/13/15.
 */
public class TableDetailFragment extends Fragment {

    public static final String TAG = TableDetailFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private FragmentDetailAdapter mFragmentDetailAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mTableName;
    private String mColumnName;

    private int mType;

    private DFDatabaseHelper mDfDatabaseHelper;
    private List<String> mStringRows;
    private List<Integer> mIntegerRows;

    public void setArguments(String tableName, String columnName) {
        this.mTableName = tableName;
        this.mColumnName = columnName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_table_detail, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.detailRecyclerView);

        initDatabaseHelper();
        initRecyclerView();

        return rootView;
    }

    private void initDatabaseHelper() {

        mFragmentDetailAdapter = new FragmentDetailAdapter();
        mDfDatabaseHelper = DFDatabaseHelper.getInstance(getActivity());
        try {
            mDfDatabaseHelper.create();
            mType = mDfDatabaseHelper.getColumnType(mTableName, mColumnName);
            switch (mType) {
                case 1:
                    mIntegerRows = mDfDatabaseHelper.getAllRows(mTableName, mColumnName, mType);
                    for (Integer integer : mIntegerRows)
                        Log.d(TAG, integer.toString());
                    mFragmentDetailAdapter.setRowIntegerValues(mIntegerRows);
                    break;
                case 3:
                    mStringRows = mDfDatabaseHelper.getAllRows(mTableName, mColumnName, mType);
                    for (String name : mStringRows)
                        Log.d(TAG, name);
                    mFragmentDetailAdapter.setRowNames(mStringRows);
                    break;
            }
        } catch (IOException e) {
            throw new Error("Unable to create database");
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mFragmentDetailAdapter);
        mRecyclerView.invalidate();
    }

}
