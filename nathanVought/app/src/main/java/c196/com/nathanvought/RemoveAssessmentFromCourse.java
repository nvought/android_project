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

public class RemoveAssessmentFromCourse extends AppCompatActivity {

    CourseAssessmentJuctionDBH courseAssessmentJuctionDBH;
    CourseDBH courseDBH;
    AssessmentDBH assessmentDBH;
    EditText dcCoursId, dcAssessmentId;
    Button deleteAssessmentToCourse;
    TextView dassessmentsToCourseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_assessment_from_course);

        courseAssessmentJuctionDBH = new CourseAssessmentJuctionDBH(this);
        courseDBH = new CourseDBH(this);
        assessmentDBH = new AssessmentDBH(this);
        dcCoursId = (EditText) findViewById(R.id.dcCoursId);
        dcAssessmentId = (EditText) findViewById(R.id.dcAssessmentId);
        deleteAssessmentToCourse = (Button) findViewById(R.id.deleteAssessmentToCourse);
        dassessmentsToCourseTextView = (TextView) findViewById(R.id.dassessmentsToCourseTextView);

        deleteAssessmentFromCourse();

    }
    public void viewHome(View v){
        Intent goHome = new Intent(RemoveAssessmentFromCourse.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backViewCourses(View v){
        Intent back = new Intent(RemoveAssessmentFromCourse.this, Courses.class);
        startActivity(back);
    }
    public void deleteAssessmentFromCourse() {
        deleteAssessmentToCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cId = dcCoursId.getText().toString();
                String aId = dcAssessmentId.getText().toString();

                Cursor courseData = courseDBH.findData(cId);
                int courseExists = courseData.getCount();
                Cursor assessmentData = assessmentDBH.findData(aId);
                int assessmentExists = assessmentData.getCount();



                if (courseExists != 0 && assessmentExists !=0) {

                    Cursor findData = courseAssessmentJuctionDBH.findCourseAndAssessmentMatch(cId,aId);
                    int matchFound = findData.getCount();

                    if(matchFound!=0) {

                        Cursor deleteData = courseAssessmentJuctionDBH.deleteData(cId, aId);
                        int deleteSuccessful = deleteData.getCount();

                        if (deleteSuccessful == 0) {
                            Toast.makeText(RemoveAssessmentFromCourse.this, "Data Successfully Deleted!", Toast.LENGTH_LONG).show();
                            getAssessmentsToCourse();

                        } else {
                            Toast.makeText(RemoveAssessmentFromCourse.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(RemoveAssessmentFromCourse.this, "This course does not contain assessment ID:"+aId, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RemoveAssessmentFromCourse.this, "The course or assessment ID you entered does not exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getAssessmentsToCourse() {
        String cid = dcCoursId.getText().toString();
        Cursor cursor = courseAssessmentJuctionDBH.findData(cid);
        String contactList = "Course " +cid +" Assessments"+ "\n";

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(cursor.getColumnIndex("COURSEID"));
                String title = cursor.getString(cursor.getColumnIndex("ASSESSMENTID"));

                contactList = contactList +"Assessment ID-" + title + "\n";

            } while (cursor.moveToNext());

        }

        dassessmentsToCourseTextView.setText(contactList);
    }
}

