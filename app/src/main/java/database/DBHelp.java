package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 21.10.2017.
 */

public class DBHelp extends SQLiteOpenHelper {

    public DBHelp(Context context) {
        // конструктор суперкласса
        super(context, "Lab_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table SimpleTable ("
                + "ID integer primary key autoincrement,"
                + "F real not null,"
                + "T text not null" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
