package c196.com.nathanvought;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by mac on 10/28/17.
 */

public class Assessments extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
    }
    public void viewHome(View v){
        Intent goHome = new Intent(Assessments.this, MainActivity.class);
        startActivity(goHome);
    }
    public void viewAssesments(View v) {
        Intent goViewTerms = new Intent(Assessments.this, ViewAssessments.class);
        startActivity(goViewTerms);
    }
    public void viewAddAssessments(View v) {
        Intent goViewAddAssessments = new Intent(Assessments.this, AddAssessments.class);
        startActivity(goViewAddAssessments);
    }
    public void viewEditAssessments(View v) {
        Intent goViewEditAssessments = new Intent(Assessments.this, EditAssessments.class);
        startActivity(goViewEditAssessments);
    }
    public void viewRemoveAssessments(View v) {
        Intent goViewRemoveAsssessments = new Intent(Assessments.this, RemoveAssessments.class);
        startActivity(goViewRemoveAsssessments);
    }
    public void viewAssessmentsAlarm(View v) {
        Intent goViewRemoveAsssessments = new Intent(Assessments.this, SetAlarmForAssessment.class);
        startActivity(goViewRemoveAsssessments);
    }
}
