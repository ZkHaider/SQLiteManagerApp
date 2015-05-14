package com.sqlitemanagerapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZkHaider on 5/13/15.
 */
public class DFDatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = DFDatabaseHelper.class.getSimpleName();
    private static final int BUFFER_SIZE = 1024;

    // Default Path
    private static String DB_PATH = "data/data/com.sqlitemanagerapp/databases/";
    private static String DB_NAME = "data_forensics.db";
    private static int DB_VERSION = 1;

    private static DFDatabaseHelper mDFDatabaseHelper;

    private final Context mContext;
    private SQLiteDatabase mDb;

    private DFDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    public static DFDatabaseHelper getInstance(Context context) {
        if (mDFDatabaseHelper == null)
            mDFDatabaseHelper = new DFDatabaseHelper(context);
        return mDFDatabaseHelper;
    }

    // Create an empty database and then copy over local database from assets
    public void create() throws IOException {

        boolean dbExist = checkDatabase();
        if (!dbExist) {
            // Empty database gets created onto the default system path
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Check if database already exists to avoid recopying database
    private boolean checkDatabase() {

        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (checkDb != null)
            checkDb.close();

        return checkDb != null;
    }

    // Copy database from the Assets directory
    private void copyDatabase() throws IOException {

        // Open the local database as input stream
        InputStream input = mContext.getAssets().open(DB_NAME);

        // Path
        String fileName = DB_PATH + DB_NAME;

        // Open empty db
        OutputStream output = new FileOutputStream(fileName);

        // Transfer bytes
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Close
        output.flush();
        output.close();
        input.close();
    }

    // Open the database
    public boolean open() {

        String path = DB_PATH + DB_NAME;

        try {
            mDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch (SQLiteException e) {
            mDb = null;
            return false;
        }

    }

    // Get all table names
    public List<String> getAllTableNames() {

        List<String> tableNames = new ArrayList<>();
        try {
            String query = "SELECT name FROM sqlite_master WHERE type='table'";
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            // Iterate
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    tableNames.add(cursor.getString(cursor.getColumnIndex("name")));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableNames;
    }

    // Get all names of columns inside a table
    public List<String> getAllColumnNames(String tableName) {

        List<String> names = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + tableName;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null, null);
            String[] columnNames = cursor.getColumnNames();
            names = Arrays.asList(columnNames);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return names;
    }

    public int getColumnType(String tableName, String columnName) {

        String[] args = { columnName };

        try {
            int type;
            String query = "SELECT * FROM " + tableName;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            Log.d(TAG, "Column Index: " + String.valueOf(cursor.getColumnIndex(columnName)));
            type = cursor.getType(cursor.getColumnIndex(columnName));
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List getAllRows(String tableName, String columnName, int type) {

        Log.d(TAG, "Getting all rows");
        switch (type) {
            case 1:
                List<Integer> integerList = createIntegerList(tableName, columnName);
                return integerList;
            case 3:
                List<String> stringList = createStringList(tableName, columnName);
                return stringList;
        }

        return null;
    }

    private List<Integer> createIntegerList(String tableName, String columnName) {

        List<Integer> integerList = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + tableName;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int number = cursor.getInt(cursor.getColumnIndex(columnName));
                    integerList.add(number);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return integerList;
    }

    private List<String> createStringList(String tableName, String columnName) {

        List<String> integerList = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + tableName;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String name = cursor.getString(cursor.getColumnIndex(columnName));
                    integerList.add(name);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return integerList;
    }

    @Override
    public synchronized void close() {
        if (mDb != null)
            mDb.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
