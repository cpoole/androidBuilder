package database;

/**
 * Created by connor on 1/20/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import DAO.menuItem;

public class FinalDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_FOOD_TYPE,
            MySQLiteHelper.COLUMN_FOOD_NAME,
            MySQLiteHelper.COLUMN_FOOD_DESCRIPTION,
            MySQLiteHelper.COLUMN_FOOD_PRICE};

    private String[] allUserColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PUNCHES};


    public FinalDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createMenuItem(String type, String title, String description, String price) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_FOOD_TYPE, type);
        values.put(MySQLiteHelper.COLUMN_FOOD_NAME, title);
        values.put(MySQLiteHelper.COLUMN_FOOD_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_FOOD_PRICE, price);

        long insertId = database.insert(MySQLiteHelper.TABLE_MENU, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MENU,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        menuItem newMenuItem = cursorToMenuItem(cursor);
        cursor.close();
    }

    public void deleteMenuItem(menuItem item) {
        long id = item.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_MENU, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<menuItem> getAllMenuItems() {
        List<menuItem> menuItems = new ArrayList<menuItem>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_MENU,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            menuItem item = cursorToMenuItem(cursor);
            menuItems.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return menuItems;
    }

    private menuItem cursorToMenuItem(Cursor cursor) {
        menuItem newItem = new menuItem(cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
        newItem.setId(cursor.getLong(0));
        return newItem;
    }

    public void createUser(String numPunches){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PUNCHES, numPunches);

        long insertId = database.insert(MySQLiteHelper.TABLE_USER, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        cursor.close();
    }

}
