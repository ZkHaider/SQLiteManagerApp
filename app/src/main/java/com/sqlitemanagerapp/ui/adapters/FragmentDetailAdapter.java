package com.sqlitemanagerapp.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sqlitemanagerapp.R;

import java.util.List;

/**
 * Created by ZkHaider on 5/13/15.
 */
public class FragmentDetailAdapter extends RecyclerView.Adapter<FragmentDetailAdapter.DetailViewHolder> {

    public static final String TAG = DataAdapter.class.getSimpleName();

    private List<String> mRowNames;
    private List<Integer> mRowIntegerValues;

    public void setRowNames(List<String> tableNames) {
        this.mRowNames = tableNames;
    }

    public String getRowName(int position) {
        return mRowNames.get(position);
    }

    public void setRowIntegerValues(List<Integer> rowIntegerValues) {
        this.mRowIntegerValues = rowIntegerValues;
    }

    public int getRowIntegerValue(int position) {
        return mRowIntegerValues.get(position);
    }

    @Override
    public FragmentDetailAdapter.DetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_data_view,
                viewGroup, false);
        return new DetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FragmentDetailAdapter.DetailViewHolder viewHolder, int position) {
        if (mRowIntegerValues != null) {
            if (mRowIntegerValues.get(position) != null) {
                int number = mRowIntegerValues.get(position);
                Log.d(TAG, String.valueOf(Integer.toString(number)));
                viewHolder.mTextView.setText(Integer.toString(number));
            }
        } else if (mRowNames != null) {
            if (mRowNames.get(position) != null) {
                String name = mRowNames.get(position);
                viewHolder.mTextView.setText(name);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mRowNames != null)
            return mRowNames.size();
        else if (mRowIntegerValues != null)
            return mRowIntegerValues.size();
        return 0;
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {

        protected TextView mTextView;

        public DetailViewHolder(View v) {
            super(v);

            mTextView = (TextView) v.findViewById(R.id.dataName);
        }

    }

}
