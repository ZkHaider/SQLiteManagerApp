package com.sqlitemanagerapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqlitemanagerapp.R;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by ZkHaider on 5/13/15.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    public static final String TAG = DataAdapter.class.getSimpleName();

    private List<String> mTableNames;

    public void setTableNames(List<String> tableNames) {
        this.mTableNames = tableNames;
    }

    public String getTableName(int position) {
        return mTableNames.get(position);
    }

    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_data_view,
                                                                        viewGroup, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DataAdapter.DataViewHolder viewHolder, int position) {
        String name = mTableNames.get(position);
        viewHolder.mDataName.setText(name);
    }

    @Override
    public int getItemCount() {
        return mTableNames.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        protected TextView mDataName;

        public DataViewHolder(View v) {
            super(v);

            mDataName = (TextView) v.findViewById(R.id.dataName);
        }

    }
}
