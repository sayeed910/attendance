package com.tahsinsayeed.attendance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tahsinsayeed.attendance.Config;
import com.tahsinsayeed.attendance.course.CourseRepository;


public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler instance;

    public static DBHandler getInstance(Context context){
        if (instance == null){
            return new DBHandler(context);
        }
        return instance;
    }


    private DBHandler(Context context){
        super(context, (String)Config.get("db_name"), null, (int)Config.get("db_version"));
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        new CourseTable(sqLiteDatabase).create();
        //All the courses are fixed
        populateTable();
    }

    private void populateTable() {
        CourseRepository courseRepository = new CourseRepository();
    }


    public static SQLiteDatabase getDB(Context context){
        return DBHandler.getInstance(context).getWritableDatabase();
    }

    @Override
    public void close(){
        DBHandler.instance = null;
        super.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        new CourseTable(sqLiteDatabase).drop();
        onCreate(sqLiteDatabase);
    }
}
