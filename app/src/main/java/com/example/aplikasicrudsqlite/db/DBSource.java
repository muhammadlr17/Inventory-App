package com.example.aplikasicrudsqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.aplikasicrudsqlite.models.Inventory;

import java.util.ArrayList;

public class DBSource {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME,
            DBHelper.COLUMN_BRAND, DBHelper.COLUMN_PRICE};

    public DBSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Inventory createInventory(String name, String brand, String price) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_BRAND, brand);
        values.put(DBHelper.COLUMN_PRICE, price);


        long insertId = database.insert(DBHelper.TABLE_NAME, null, values);

        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns,
                DBHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null);

        cursor.moveToFirst();

        Inventory newInventory = cursorToInventory(cursor);
        cursor.close();

        return newInventory;

    }

    private Inventory cursorToInventory(Cursor cursor) {
        Inventory inventory = new Inventory();

        inventory.setId(cursor.getLong(0));
        inventory.setName(cursor.getString(1));
        inventory.setBrand(cursor.getString(2));
        inventory.setPrice(cursor.getString(3));

        return inventory;

    }

    public ArrayList<Inventory> getAllInventory() {
        ArrayList<Inventory> listInventory = new ArrayList<Inventory>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns,
                null, null, null, null, null);


        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Inventory inventory = cursorToInventory(cursor);
            listInventory.add(inventory);
            cursor.moveToNext();
        }
        cursor.close();
        return listInventory;

    }

    public Inventory getInventory(long id) {
        Inventory inventory = new Inventory();
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                allColumns,
                "_id =" + id, null, null, null,
                null);

        cursor.moveToFirst();
        inventory = cursorToInventory(cursor);

        cursor.close();
        return inventory;
    }


    public void updateInventory(Inventory inventory) {
        String strFilter = "_id=" + inventory.getId();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COLUMN_NAME, inventory.getName());
        contentValues.put(DBHelper.COLUMN_PRICE, inventory.getPrice());
        contentValues.put(DBHelper.COLUMN_BRAND, inventory.getBrand());

        database.update(DBHelper.TABLE_NAME, contentValues, strFilter, null);


    }

    public void deleteInventory(long id) {
        String strFilter = "_id=" + id;


        database.delete(DBHelper.TABLE_NAME, strFilter, null);
    }
}
