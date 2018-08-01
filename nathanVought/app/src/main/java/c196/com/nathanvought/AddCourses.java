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

public class AddCourses extends AppCompatActivity {
    CourseDBH courseDBH;
    EditText courseTitle, courseStartDate, courseEndDate, courseMentorName, courseMentorEmail, courseMentorPhone;
    TextView coursesTextView;
    Button addCourse;
    Spinner courseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        courseDBH = new CourseDBH(this);

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

        addCourse();




    }

    public void viewHome(View v){
        Intent goHome = new Intent(AddCourses.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backAddCourses(View v){
        Intent back = new Intent(AddCourses.this, Courses.class);
        startActivity(back);
    }
    public void addCourse() {
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = courseTitle.getText().toString();
                String sdate = courseStartDate.getText().toString();
                String edate = courseEndDate.getText().toString();
                String status = courseStatus.getSelectedItem().toString();
                String cmname = courseMentorName.getText().toString();
                String cmemail = courseMentorEmail.getText().toString();
                String cmphone = courseMentorPhone.getText().toString();

                if(!title.isEmpty() && !sdate.isEmpty() && !status.isEmpty() && !cmname.isEmpty() && !cmemail.isEmpty() && !cmphone.isEmpty()){
                if (edate.matches("\\d{2}-\\d{2}-\\d{4}") && sdate.matches("\\d{2}-\\d{2}-\\d{4}")) {


                    boolean insertData = courseDBH.addData(title, sdate, edate, status, cmname, cmemail, cmphone);

                    if (insertData == true) {
                        Toast.makeText(AddCourses.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                        getCourses();

                    } else {
                        Toast.makeText(AddCourses.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                    }
                } else {

                    Toast.makeText(AddCourses.this, "Date format must be mm-dd-yyyy example 12-17-2017", Toast.LENGTH_LONG).show();
                }

            } else{

                    Toast.makeText(AddCourses.this, "Please make sure all fields are complete", Toast.LENGTH_LONG).show();

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
