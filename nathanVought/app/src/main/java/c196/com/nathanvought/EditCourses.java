package c196.com.nathanvought;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mac on 10/28/17.
 */

public class EditCourses extends AppCompatActivity {
    CourseDBH courseDBH;
    EditText courseId,courseTitle, courseStartDate, courseEndDate, courseMentorName, courseMentorEmail, courseMentorPhone;
    TextView coursesTextView;
    Button addCourse;
    Spinner courseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_courses);
        courseDBH = new CourseDBH(this);

        courseId = (EditText) findViewById(R.id.courseId);
        courseTitle = (EditText) findViewById(R.id.courseTitle);
        courseStartDate = (EditText) findViewById(R.id.courseStartDate);
        courseEndDate = (EditText) findViewById(R.id.courseEndDate);
        courseStatus = (Spinner) findViewById(R.id.courseStatus);
        courseMentorName = (EditText) findViewById(R.id.courseMentorName);
        courseMentorEmail = (EditText) findViewById(R.id.courseMentorEmail);
        courseMentorPhone = (EditText) findViewById(R.id.courseMentorPhone);
        coursesTextView = (TextView) findViewById(R.id.coursesTextView);
        addCourse = (Button) findViewById(R.id.addCourse);

        String[] items = new String[]{"In Progress", "Completed", "Plan to Take", "Dropped"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        courseStatus.setAdapter(adapter);
        editCourse();
    }

    public void viewHome(View v){
        Intent goHome = new Intent(EditCourses.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backEditCourses(View v){
        Intent back = new Intent(EditCourses.this, Courses.class);
        startActivity(back);
    }
    public void editCourse() {
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = courseId.getText().toString();
                String title = courseTitle.getText().toString();
                String sdate = courseStartDate.getText().toString();
                String edate = courseEndDate.getText().toString();
                String status = courseStatus.getSelectedItem().toString();
                String cmname = courseMentorName.getText().toString();
                String cmemail = courseMentorEmail.getText().toString();
                String cmphone = courseMentorPhone.getText().toString();
                if(!id.isEmpty() && !title.isEmpty() && !sdate.isEmpty() && !edate.isEmpty() && !status.isEmpty() && !cmname.isEmpty() && !cmemail.isEmpty() && !cmphone.isEmpty()) {
                    if (sdate.matches("\\d{2}-\\d{2}-\\d{4}") && edate.matches("\\d{2}-\\d{2}-\\d{4}")) {


                        boolean insertData = courseDBH.updateData(id, title, sdate, edate, status, cmname, cmemail, cmphone);

                        if (insertData == true) {
                            Toast.makeText(EditCourses.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                            getCourses();

                        } else {
                            Toast.makeText(EditCourses.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(EditCourses.this, "Date format is incorrect. Dates should be MM-DD-YYYY", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(EditCourses.this, "Missing information", Toast.LENGTH_LONG).show();
                }

            }
        });
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

        coursesTextView.setText(contactList);
    }
}
