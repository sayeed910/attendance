package com.tahsinsayeed.attendance.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.tahsinsayeed.attendance.R;
import com.tahsinsayeed.attendance.course.Course;
import com.tahsinsayeed.attendance.course.CourseRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> courses = new ArrayList<>();
    SimpleAdapter adapter;
    private List<Course> allCourses;
    private ListView lstCourses;
    private TextView txtTotalAttendance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lstCourses = (ListView) findViewById(R.id.courses);
        txtTotalAttendance = (TextView) findViewById(R.id.txtTotalAttendance);

        CourseRepository courseRepository = new CourseRepository();

        courseRepository.insert(getApplicationContext(), new Course("CSE-3201", "Software Design Pattern"));
        courseRepository.insert(getApplicationContext(), new Course("CSE-3202", "Computer Networking"));
        courseRepository.insert(getApplicationContext(), new Course("CSE-3203", "Finite Language, Automata & Computation"));
        courseRepository.insert(getApplicationContext(), new Course("CSE-3204", "System Programming"));
        courseRepository.insert(getApplicationContext(), new Course("CSE-3205", "Mathematics for Computer Science"));

        allCourses = courseRepository.getAll(this.getApplicationContext());

        populateUI();




    }

    private void populateUI() {
        txtTotalAttendance.setText(getTotalAttendance());
        populateListView(lstCourses);
    }

    private String getTotalAttendance() {
        double totalAttendance = 0;
        for (Course course : allCourses) {
            totalAttendance += course.getAttendancePercentage();
        }
        return String.format("%.2f%%", totalAttendance / allCourses.size());
    }

    private void populateListView(final ListView lstCourses) {
        courses.clear();

        for (Course course : allCourses) {
            courses.add(getPropertyMap(course));
        }

        adapter = new SimpleAdapter(this, courses, R.layout.courses,
                new String[]{"title", "stat"}, new int[]{R.id.courseName, R.id.courseStat}) {
            @Override
            public View getView(final int position, View convertView, ViewGroup viewGroup) {
                View itemView = super.getView(position, convertView, viewGroup);
                ImageButton btnPresent = (ImageButton) itemView.findViewById(R.id.btnPresent);
                ImageButton btnAbsent = (ImageButton) itemView.findViewById(R.id.btnAbsent);

                btnPresent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        allCourses.get(position).attendClass(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "" + allCourses.get(position).getClassHeld(), Toast.LENGTH_LONG).show();
                        populateListView(lstCourses);
                    }
                });


                btnAbsent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        allCourses.get(position).skipClass(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "" + allCourses.get(position).getClassHeld(), Toast.LENGTH_LONG).show();
                        populateListView(lstCourses);
                    }
                });


                return itemView;
            }
        };

        lstCourses.setAdapter(adapter);
    }


    private HashMap<String, String> getPropertyMap(Course course) {
        HashMap<String, String> map = new HashMap<>();
        String classAttended = String.valueOf(course.getClassAttended());
        String classHeld = String.valueOf(course.getClassHeld());
        String attendance = String.format("%.2f%%", course.getAttendancePercentage());

        map.put("title", String.format("[%s] %s", course.getId(), course.getName()));
        map.put("stat", String.format("Attendance: (%s/%s) %s", classAttended, classHeld, attendance));

        return map;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
