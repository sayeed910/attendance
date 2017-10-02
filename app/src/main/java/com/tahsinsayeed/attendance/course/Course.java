package com.tahsinsayeed.attendance.course;


import android.content.Context;

import java.util.HashMap;

public class Course {

    private final String id;
    private final String name;
    private int classHeld;
    private int classAttended;

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
        this.classAttended = 0;
        this.classHeld = 0;
    }

    public Course(String id, String name, int classHeld, int classAttended) {
        this.id = id;
        this.name = name;
        this.classAttended = classAttended;
        this.classHeld = classHeld;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClassHeld() {
        return classHeld;
    }

    public int getClassAttended() {
        return classAttended;
    }

    public double getAttendancePercentage() {
        if (classHeld == 0) return 0;
        return (classAttended / (double)classHeld) * 100;

    }

    public void attendClass(Context context){
        this.classHeld++;
        this.classAttended++;
        new CourseRepository().update(context, this);
    }

    public void skipClass(Context context){
        this.classHeld++;
        new CourseRepository().update(context, this);
    }

}
