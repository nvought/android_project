package c196.com.nathanvought;

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

public class RemoveCourses extends AppCompatActivity {
    CourseDBH courseDBH;
    CourseAssessmentJuctionDBH courseAssessmentJuctionDBH;
    EditText deleteId;
    Button removeId;
    TextView courseTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_courses);
        courseDBH = new CourseDBH(this);
        courseAssessmentJuctionDBH = new CourseAssessmentJuctionDBH(this);
        deleteId = (EditText) findViewById(R.id.deleteId);
        removeId = (Button) findViewById(R.id.removeId);
        courseTextView = (TextView) findViewById(R.id.courseTextView);
        deleteData();
    }

    public void viewHome(View v){
        Intent goHome = new Intent(RemoveCourses.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backRemoveCourses(View v){
        Intent back = new Intent(RemoveCourses.this, Courses.class);
        startActivity(back);
    }
    public void deleteData() {
        removeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = deleteId.getText().toString().length();
                Cursor courseAssesmentData = courseAssessmentJuctionDBH.findData(deleteId.getText().toString());
                int courseHasAssessment = courseAssesmentData.getCount();
                int courseExists = courseDBH.findData(deleteId.getText().toString()).getCount();

                if(deleteId.getText().toString().isEmpty()){
                    Toast.makeText(RemoveCourses.this, "Please enter an ID to delete", Toast.LENGTH_LONG).show();


                } else {
                    if (courseExists > 0) {
                        if (courseHasAssessment == 0) {

                            Integer deleteRow = courseDBH.deleteData(deleteId.getText().toString());
                            if (deleteRow > 0) {
                                Toast.makeText(RemoveCourses.this, "Successfully Deleted The Data!", Toast.LENGTH_LONG).show();
                                getCourses();
                            } else {
                                Toast.makeText(RemoveCourses.this, "Something went wrong", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(RemoveCourses.this, "You cannot delete this course because it has an Assessment. Please remove assessment then delete course.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RemoveCourses.this, "Course ID does not exist", Toast.LENGTH_LONG).show();
                    }
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

        courseTextView.setText(contactList);
    }
}
