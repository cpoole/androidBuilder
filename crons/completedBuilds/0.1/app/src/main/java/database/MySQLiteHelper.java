package database;

/**
 * Created by connor on 1/20/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MENU = "menu";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FOOD_TYPE = "type";
    public static final String COLUMN_FOOD_NAME = "name";
    public static final String COLUMN_FOOD_DESCRIPTION = "description";
    public static final String COLUMN_FOOD_PRICE = "price";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_PUNCHES = "punches";

    private static final String DATABASE_NAME = "final.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statements
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MENU + "(" + COLUMN_ID
            + " integer primary key autoincrement, " +
            COLUMN_FOOD_TYPE + " text not null," +
            COLUMN_FOOD_NAME + " text not null," +
            COLUMN_FOOD_DESCRIPTION + " text not null," +
            COLUMN_FOOD_PRICE + " text not null);";

    private static final String USER_DATABASE_CREATE = "create table "
            + TABLE_USER + "(" + COLUMN_ID
            + " integer primary key autoincrement, " +
            COLUMN_PUNCHES + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(USER_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

}