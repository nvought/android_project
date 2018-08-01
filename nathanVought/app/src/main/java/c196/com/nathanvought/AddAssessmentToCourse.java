package c196.com.nathanvought;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAssessmentToCourse extends AppCompatActivity {

    CourseAssessmentJuctionDBH courseAssessmentJuctionDBH;
    CourseDBH courseDBH;
    AssessmentDBH assessmentDBH;
    EditText acCoursId, acAssessmentId;
    Button addAssessmentToCourse;
    TextView assessmentsToCourseTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment_to_course);

        courseAssessmentJuctionDBH = new CourseAssessmentJuctionDBH(this);
        courseDBH = new CourseDBH(this);
        assessmentDBH = new AssessmentDBH(this);
        acCoursId = (EditText) findViewById(R.id.acCoursId);
        acAssessmentId = (EditText) findViewById(R.id.acAssessmentId);
        addAssessmentToCourse = (Button) findViewById(R.id.addAssessmentToCourse);
        assessmentsToCourseTextView = (TextView) findViewById(R.id.assessmentsToCourseTextView);
        addAssessmentToCourse();

    }
    public void viewHome(View v){
        Intent goHome = new Intent(AddAssessmentToCourse.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backViewCourses(View v){
        Intent back = new Intent(AddAssessmentToCourse.this, Courses.class);
        startActivity(back);
    }
    public void addAssessmentToCourse() {
        addAssessmentToCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cId = acCoursId.getText().toString();
                String aId = acAssessmentId.getText().toString();

                Cursor courseData = courseDBH.findData(cId);
                int courseExists = courseData.getCount();
                Cursor assessmentData = assessmentDBH.findData(aId);
                int assessmentExists = assessmentData.getCount();

                if (courseExists != 0 && assessmentExists !=0) {

                    Cursor findData = courseAssessmentJuctionDBH.findCourseAndAssessmentMatch(cId,aId);
                    int matchFound = findData.getCount();

                    if(matchFound==0) {


                        boolean insertData = courseAssessmentJuctionDBH.addData(cId, aId);

                        if (insertData == true) {
                            Toast.makeText(AddAssessmentToCourse.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                            getAssessmentsToCourse();

                        } else {
                            Toast.makeText(AddAssessmentToCourse.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(AddAssessmentToCourse.this, "This course already contains assessment ID: " +aId, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddAssessmentToCourse.this, "The course or assessment ID you entered does not exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getAssessmentsToCourse() {
        String cid = acCoursId.getText().toString();
        Cursor cursor = courseAssessmentJuctionDBH.findData(cid);
        String contactList = "Course " +cid +" Assessments"+ "\n";

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(cursor.getColumnIndex("COURSEID"));
                String title = cursor.getString(cursor.getColumnIndex("ASSESSMENTID"));

                contactList = contactList +"Assessment ID-" + title + "\n";

            } while (cursor.moveToNext());

        }

        assessmentsToCourseTextView.setText(contactList);
    }
}
