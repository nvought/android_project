package c196.com.nathanvought;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by mac on 10/28/17.
 */

public class Courses extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
    }
    public void viewHome(View v){
        Intent goHome = new Intent(Courses.this, MainActivity.class);
        startActivity(goHome);
    }
    public void viewCourses(View v) {
        Intent goViewCourses = new Intent(Courses.this, ViewCourses.class);
        startActivity(goViewCourses);
    }
    public void viewAddCourses(View v) {
        Intent goViewAddCourses = new Intent(Courses.this, AddCourses.class);
        startActivity(goViewAddCourses);
    }
    public void viewEditCourses(View v) {
        Intent goViewEditCourses = new Intent(Courses.this, EditCourses.class);
        startActivity(goViewEditCourses);
    }
    public void viewRemoveCourses(View v) {
        Intent goViewRemoveCourses = new Intent(Courses.this, RemoveCourses.class);
        startActivity(goViewRemoveCourses);
    }

    public void viewAddAssessmentToCourses(View v) {
        Intent goViewRemoveCourses = new Intent(Courses.this, AddAssessmentToCourse.class);
        startActivity(goViewRemoveCourses);
    }

    public void viewRemoveAssessmentToCourses(View v) {
        Intent goViewRemoveCourses = new Intent(Courses.this, RemoveAssessmentFromCourse.class);
        startActivity(goViewRemoveCourses);
    }
    public void viewEmailNotesAboutCourses(View v) {
        Intent goViewRemoveCourses = new Intent(Courses.this, EmailNotesAboutCourse.class);
        startActivity(goViewRemoveCourses);
    }
    public void viewSetAlarmForCourse(View v) {
        Intent goViewRemoveCourses = new Intent(Courses.this, SetAlarmForCourse.class);
        startActivity(goViewRemoveCourses);
    }
}
