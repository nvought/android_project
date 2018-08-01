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

/**
 * Created by mac on 10/28/17.
 */

public class ViewTerms extends AppCompatActivity {
    TermsDBH termsDBH;
    CourseDBH courseDBH;
    TermsCourseJuctionDBH termsCourseJuctionDBH;
    EditText byTermId;
    Button viewTermById;
    TextView viewTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_terms);

        termsDBH = new TermsDBH(this);
        courseDBH = new CourseDBH(this);
        termsCourseJuctionDBH = new TermsCourseJuctionDBH(this);
        byTermId = (EditText) findViewById(R.id.byTermId);
        viewTermById = (Button) findViewById(R.id.viewTermById);
        viewTerms = (TextView) findViewById(R.id.viewTerms);
        viewSingleData();

    }

    public void viewHome(View v){
        Intent goHome = new Intent(ViewTerms.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backViewTerms(View v){
        Intent back = new Intent(ViewTerms.this, Terms.class);
        startActivity(back);
    }
    public void viewSingleData() {
        viewTermById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tid = byTermId.getText().toString();
                Cursor data = termsDBH.findData(tid);
                String courses = getCourseToTerms(tid);

                if (!tid.isEmpty()) {


                    if (data.getCount() == 0) {
                        display("The term does not exist", "Please check the term ID");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (data.moveToNext()) {


                        buffer.append("ID: " + data.getString(0) + "\n");
                        buffer.append("Title: " + data.getString(1) + "\n");
                        buffer.append("Start Date: " + data.getString(2) + "\n");
                        buffer.append("End Date: " + data.getString(3) + "\n");
                        buffer.append(courses+"\n");


                        display("Term: ", buffer.toString());
                        getTerms();
                    }
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

    public void getTerms() {
        Cursor cursor = termsDBH.showData();
        String contactList = "";

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(cursor.getColumnIndex("ID"));
                String title = cursor.getString(cursor.getColumnIndex("TITLE"));

                contactList = contactList + id + "  :  " + title + "\n";

            } while (cursor.moveToNext());

        }

        viewTerms.setText(contactList);
    }

    public String getCourseToTerms(String tid) {

        Cursor cursor = termsCourseJuctionDBH.findData(tid);
        String courses = "";

        if (cursor.moveToFirst()) {

            do {


                String courseid = cursor.getString(cursor.getColumnIndex("COURSEID"));

                String courseNames = getCourseTitles(courseid);

                courses = courses  + courseNames;

            } while (cursor.moveToNext());
            return courses;

        }

        return courses;
    }

    public String getCourseTitles(String cid) {


        Cursor data = courseDBH.findData(cid);
        String courses = "";

        if (!cid.isEmpty()) {



            while (data.moveToNext()) {
                courses = courses + "Course "+cid+" : " + data.getString(1)+ "\n";


            }

            return courses;
        }

        return courses;

    }
}
