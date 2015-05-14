package com.sqlitemanagerapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.sqlitemanagerapp.R;
import com.sqlitemanagerapp.database.DFDatabaseHelper;
import com.sqlitemanagerapp.ui.adapters.DataAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    // Views
    @InjectView(R.id.databaseRecyclerView)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private DataAdapter mDataAdapter;

    private DFDatabaseHelper mDFDatabasehelper;
    private List<String> mTableNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initDatabaseHelper();
        initRecyclerView();
        initDataIntoView();
        initItemTouchListener();
    }

    private void initDatabaseHelper() {
        mDFDatabasehelper = DFDatabaseHelper.getInstance(this);
        try {
            mDFDatabasehelper.create();
            mTableNames = mDFDatabasehelper.getAllTableNames();
        } catch (IOException e) {
            throw new Error("Unable to create database");
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void initDataIntoView() {
        mDataAdapter = new DataAdapter();
        for (String name : mTableNames)
            Log.d(TAG, name);
        mDataAdapter.setTableNames(mTableNames);
        mRecyclerView.setAdapter(mDataAdapter);
        mRecyclerView.invalidate();
    }

    private void initItemTouchListener() {
        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }
                    });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    String tableName = mDataAdapter.getTableName(position);

                    Intent i = new Intent(MainActivity.this, TableDetailActivity.class);
                    // Kind of overkill here.
                    Bundle bundle = new Bundle();
                    bundle.putString("table_name", tableName);
                    i.putExtras(bundle);

                    startActivity(i);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
