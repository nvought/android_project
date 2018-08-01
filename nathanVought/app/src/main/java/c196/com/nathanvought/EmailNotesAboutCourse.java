package c196.com.nathanvought;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailNotesAboutCourse extends AppCompatActivity {
    CourseDBH courseDBH;
    CourseAssessmentJuctionDBH courseAssessmentJuctionDBH;
    AssessmentDBH assessmentDBH;
    EditText cIDToSend,notesToSend;
    Button sendEmail,saveNotes,viewNotes;
    String courseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_notes_about_course);

        courseDBH = new CourseDBH(this);
        courseAssessmentJuctionDBH = new CourseAssessmentJuctionDBH(this);
        assessmentDBH = new AssessmentDBH(this);

        cIDToSend = (EditText) findViewById(R.id.cIDToSend);
        notesToSend = (EditText) findViewById(R.id.notesToSend);
        sendEmail = (Button) findViewById(R.id.sendEmail);
        saveNotes = (Button) findViewById(R.id.saveNotes);
        viewNotes = (Button) findViewById(R.id.viewNotes);

        saveNotes();
        viewNotes();






    }
    public void viewHome(View v){
        Intent goHome = new Intent(EmailNotesAboutCourse.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backAddCourses(View v){
        Intent back = new Intent(EmailNotesAboutCourse.this, Courses.class);
        startActivity(back);
    }

    public void saveNotes(){
        saveNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = cIDToSend.getText().toString();
                String notes = notesToSend.getText().toString();
                if (cid.isEmpty() || notes.isEmpty()) {
                    Toast.makeText(EmailNotesAboutCourse.this, "Please enter in a course ID and the notes you want to save", Toast.LENGTH_LONG).show();

                } else {

                    int courseExists = courseDBH.findData(cid).getCount();

                    if (courseExists > 0) {


                        SharedPreferences notesToSave = getSharedPreferences("Notes", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = notesToSave.edit();
                        editor.putString(cid, notes);
                        editor.apply();
                        Toast.makeText(EmailNotesAboutCourse.this, "Course ID:"+cid+" notes saved", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EmailNotesAboutCourse.this, "Course does not exist", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void viewNotes() {
        viewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid = cIDToSend.getText().toString();
                if (cid.isEmpty()) {
                    Toast.makeText(EmailNotesAboutCourse.this, "Please enter a course ID", Toast.LENGTH_LONG).show();

                } else {
                    SharedPreferences notes = getSharedPreferences("Notes", Context.MODE_PRIVATE);
                    String note = notes.getString(cid, "There are no saved notes for this course");

                    display("Course ID:" + cid + " Notes", note);

                }
            }
        });
    }

    public void sendEmail(View v){

        String cid = cIDToSend.getText().toString();
        String csInfo = getCourseInfo();
        SharedPreferences note = getSharedPreferences("Notes", Context.MODE_PRIVATE);
        String notes = note.getString(cid, "");
        String cID= cIDToSend.getText().toString();

        if(cid.isEmpty()){
            Toast.makeText(EmailNotesAboutCourse.this, "Please enter a course ID", Toast.LENGTH_LONG).show();

        } else if (notes == "") {
            display("This course does not have any saved notes", "Please make sure you save the notes before sending");
        }
         else {


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Course ID:" + cID + " Notes");
                intent.putExtra(Intent.EXTRA_TEXT, csInfo +"\n"+"Course Notes:"+"\n"+ notes);
                intent.setType("message/rfc822");
                Intent chooser = Intent.createChooser(intent, "Email Course Notes");
                startActivity(chooser);

            }
        }




    public String getCourseInfo() {


        String cid = cIDToSend.getText().toString();
        Cursor data = courseDBH.findData(cid);
        String assessments = getAssessmentsToCourse(cid);

        if (!cid.isEmpty()) {


            if (data.getCount() == 0) {
                display("Course ID Not Found", "Please make sure the course exists before sending notes");

            }

            while (data.moveToNext()) {

                String id = "ID: " + data.getString(0) + "\n";
                String title = "Title: " + data.getString(1) + "\n";
                String sd = "Start Date: " + data.getString(2) + "\n";
                String ed = "End Date: " + data.getString(3) + "\n";
                String status = "Status: " + data.getString(4) + "\n";
                String cm = "Course Mentor Name: " + data.getString(5) + "\n";
                String cme = "Course Mentor Email: " + data.getString(6) + "\n";
                String cmp = "Course Mentor Phone: " + data.getString(7) + "\n";
                String assm = assessments + "\n";

                courseInfo = id + title + sd + ed + status + cm + cme + cmp + assm;


            }
        }
        return courseInfo;
    }


    public void display(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

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
