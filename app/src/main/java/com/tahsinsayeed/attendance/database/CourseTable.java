package com.tahsinsayeed.attendance.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sayeed on 7/8/17.
 */
public class CourseTable {
    private SQLiteDatabase database;

    public final static String TABLE_NAME = "courses";
    public final static String COLUMN_ID = "course_id";
    public final static String COLUMN_NAME = "course_name";
    public final static String COLUMN_CLASS_COUNT = "class_held";
    public final static String COLUMN_CLASS_ATTENDED = "course_attended";

    public CourseTable(SQLiteDatabase database){
        this.database = database;
    }

    public void create(){
        String createTableQuery = "create table " + TABLE_NAME +
                " ( " +
                COLUMN_ID + " text primary key, " +
                COLUMN_NAME + " text, " +
                COLUMN_CLASS_COUNT + " integer, " +
                COLUMN_CLASS_ATTENDED + " integer " +
                " )";
        database.execSQL(createTableQuery);
    }

    public void drop(){
        String dropTableIfExistsQuery = "drop table if exists " + TABLE_NAME;
        database.execSQL(dropTableIfExistsQuery);
    }
}
