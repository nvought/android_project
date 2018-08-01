package c196.com.nathanvought;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mac on 10/28/17.
 */

public class ViewCourses extends AppCompatActivity {
    CourseDBH courseDBH;
    CourseAssessmentJuctionDBH courseAssessmentJuctionDBH;
    AssessmentDBH assessmentDBH;
    TextView viewCourses;
    Button viewById;
    EditText byId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        courseDBH = new CourseDBH(this);
        courseAssessmentJuctionDBH = new CourseAssessmentJuctionDBH(this);
        assessmentDBH = new AssessmentDBH(this);


        viewCourses = (TextView) findViewById(R.id.viewCourses);
        viewById = (Button) findViewById(R.id.viewById);
        byId = (EditText) findViewById(R.id.byId);

        viewSingleData();
    }

    public void viewHome(View v) {
        Intent goHome = new Intent(ViewCourses.this, MainActivity.class);
        startActivity(goHome);
    }

    public void backViewCourses(View v) {
        Intent back = new Intent(ViewCourses.this, Courses.class);
        startActivity(back);
    }

    public void viewSingleData() {
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cid = byId.getText().toString();
                Cursor data = courseDBH.findData(cid);
                String assessments = getAssessmentsToCourse(cid);

                if (!cid.isEmpty() || cid != "") {


                    if (data.getCount() == 0) {
                        display("No course with this ID", "Please check Course ID");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (data.moveToNext()) {


                        buffer.append("ID: " + data.getString(0) + "\n");
                        buffer.append("Title: " + data.getString(1) + "\n");
                        buffer.append("Start Date: " + data.getString(2) + "\n");
                        buffer.append("End Date: " + data.getString(3) + "\n");
                        buffer.append("Status: " + data.getString(4) + "\n");
                        buffer.append("Course Mentor Name: " + data.getString(5) + "\n");
                        buffer.append("Course Mentor Email: " + data.getString(6) + "\n");
                        buffer.append("Course Mentor Phone: " + data.getString(7) + "\n");
                        buffer.append(assessments+"\n");


                        display("Course: ", buffer.toString());
                        getCourses();
                    }
                } else{
                    display("Please enter an ID", "ID is null");
                    return;
                }
            }
        });
    }

    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void getCourses() {
        Cursor cursor = courseDBH.showData();
        String contactList = "";

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(cursor.getColumnIndex("ID"));
                String title = cursor.getString(cursor.getColumnIndex("TITLE"));

                contactList = contactList + id + "  :  " + title + "\n";

            } while (cursor.moveToNext());

        }

        viewCourses.setText(contactList);
    }

    public String getAssessmentsToCourse(String cid) {

        Cursor cursor = courseAssessmentJuctionDBH.findData(cid);
        String assessments = "";

        if (cursor.moveToFirst()) {

            do {


                String assessmentid = cursor.getString(cursor.getColumnIndex("ASSESSMENTID"));

                String assessmentNames = getAssessmentTitles(assessmentid);

                assessments = assessments  + assessmentNames;

            } while (cursor.moveToNext());
            return assessments;

        }

        return assessments;
    }

    public String getAssessmentTitles(String aid) {


        Cursor data = assessmentDBH.findData(aid);
        String assessments = "";

        if (!aid.isEmpty()) {



            while (data.moveToNext()) {
                assessments = assessments + "Assessment "+aid+" : " + data.getString(1)+ "\n";


            }

            return assessments;
        }

        return assessments;

    }

}