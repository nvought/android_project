package c196.com.nathanvought;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewTermsHome(View v) {
        Intent goTerms = new Intent(MainActivity.this, Terms.class);
        startActivity(goTerms);
    }
    public void viewCoursesHome(View v) {
        Intent goCourses = new Intent(MainActivity.this, Courses.class);
        startActivity(goCourses);
    }
    public void viewAssessmentsHome(View v) {
        Intent goAssessments = new Intent(MainActivity.this, Assessments.class);
        startActivity(goAssessments);
    }
    public void viewProgress(View v) {
        Intent goAssessments = new Intent(MainActivity.this, Progress.class);
        startActivity(goAssessments);
    }
}
