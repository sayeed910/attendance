package com.tahsinsayeed.attendance.course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tahsinsayeed.attendance.database.CourseTable;
import com.tahsinsayeed.attendance.database.DBHandler;
import com.tahsinsayeed.attendance.exception.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class CourseRepository {
    public void insert(Context context, Course course) {
        SQLiteDatabase db = DBHandler.getDB(context);
        ContentValues courseData = new ContentValues();
        courseData.put(CourseTable.COLUMN_ID, course.getId());
        courseData.put(CourseTable.COLUMN_NAME, course.getName());
        courseData.put(CourseTable.COLUMN_CLASS_COUNT, course.getClassHeld());
        courseData.put(CourseTable.COLUMN_CLASS_ATTENDED, course.getClassAttended());
        try {
            db.insert(CourseTable.TABLE_NAME, null, courseData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void delete(Context context, String id) {
        SQLiteDatabase db = DBHandler.getDB(context);
        db.delete(CourseTable.TABLE_NAME, CourseTable.COLUMN_ID + " = ?",
                new String[]{id});

    }

    public void update(Context context, Course course) {
        SQLiteDatabase db = DBHandler.getDB(context);
        ContentValues updatedCourseData = new ContentValues();
        updatedCourseData.put(CourseTable.COLUMN_CLASS_COUNT, course.getClassHeld());
        updatedCourseData.put(CourseTable.COLUMN_CLASS_ATTENDED, course.getClassAttended());
        db.update(CourseTable.TABLE_NAME, updatedCourseData, CourseTable.COLUMN_ID + " = ?", new String[]{course.getId()});
    }

    public Course get(Context context, String id) throws DataNotFoundException {
        SQLiteDatabase db = DBHandler.getDB(context);

        Cursor cursor = db.query(CourseTable.TABLE_NAME, new String[]{CourseTable.COLUMN_ID, CourseTable.COLUMN_NAME,
                        CourseTable.COLUMN_CLASS_COUNT, CourseTable.COLUMN_CLASS_ATTENDED},
                CourseTable.COLUMN_ID + " = ?",
                new String[]{id}, null, null, null, null);

        if (cursor != null) cursor.moveToFirst();
        else throw new DataNotFoundException("Requested course could not be found");
        return new Course(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));

    }

    public List<Course> getAll(Context context) {
        List<Course> courses = new ArrayList<>();

        SQLiteDatabase db = DBHandler.getDB(context);

        Cursor cursor = db.rawQuery("select * from " + CourseTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                courses.add(new Course(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }

        return courses;

    }

}
