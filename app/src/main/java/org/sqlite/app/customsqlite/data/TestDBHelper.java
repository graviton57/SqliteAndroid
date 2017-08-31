package org.sqlite.app.customsqlite.data;

import android.content.Context;
import android.util.Log;

import org.sqlite.database.DatabaseErrorHandler;
import org.sqlite.database.sqlite.SQLiteDatabase;
import org.sqlite.database.sqlite.SQLiteOpenHelper;

import java.io.File;


/**
 * Created by Igor Havrylyuk on 28.08.2017.
 */

public class TestDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public TestDBHelper(Context context, String dbName) {
        super(context, dbName , null, DATABASE_VERSION);
        Log.d("TestDBHelper", "Constructor db path=" + dbName);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("TestDBHelper", "onCreate" );
        db.execSQL("CREATE TABLE salaries (per_moth INTEGER , job_title TEXT )");
        db.execSQL("INSERT INTO salaries VALUES (900, 'Junior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (1500, 'Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (3200, 'Senior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (800, 'Junior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (1300, 'Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (200, 'Senior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (600, 'Junior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (1200, 'Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (2400, 'Senior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (400, 'Junior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (1500, 'Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (2700, 'Senior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (300, 'Junior Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (1500, 'Software Engineer')");
        db.execSQL("INSERT INTO salaries VALUES (2600, 'Senior Software Engineer')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
