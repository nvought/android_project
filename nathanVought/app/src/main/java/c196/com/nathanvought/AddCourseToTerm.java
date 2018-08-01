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

public class AddCourseToTerm extends AppCompatActivity {
    TextView aCourseToTermTextView;
    Button addCourseToTerm;
    EditText aCourseId,aTermId;
    CourseDBH courseDBH;
    TermsDBH termsDBH;
    TermsCourseJuctionDBH termsCourseJuctionDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course_to_term);

        aCourseToTermTextView = (TextView) findViewById(R.id.aCourseToTermTextView);
        addCourseToTerm = (Button) findViewById(R.id.addCourseToTerm);
        aCourseId = (EditText) findViewById(R.id.aCourseId);
        aTermId = (EditText) findViewById(R.id.aTermId);
        courseDBH = new CourseDBH(this);
        termsDBH = new TermsDBH(this);
        termsCourseJuctionDBH = new TermsCourseJuctionDBH(this);
        addCourseToTerm();

    }

    public void viewHome(View v) {
        Intent goHome = new Intent(AddCourseToTerm.this, MainActivity.class);
        startActivity(goHome);
    }

    public void backViewTerms(View v){
        Intent back = new Intent(AddCourseToTerm.this, Terms.class);
        startActivity(back);
    }
    public void addCourseToTerm() {
        addCourseToTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cId = aTermId.getText().toString();
                String aId = aCourseId.getText().toString();

                Cursor courseData = termsDBH.findData(cId);
                int courseExists = courseData.getCount();
                Cursor assessmentData = courseDBH.findData(aId);
                int assessmentExists = assessmentData.getCount();

                if (courseExists != 0 && assessmentExists !=0) {

                    Cursor findData = termsCourseJuctionDBH.findCourseAndAssessmentMatch(cId,aId);
                    int matchFound = findData.getCount();

                    if(matchFound==0) {


                        boolean insertData = termsCourseJuctionDBH.addData(cId, aId);

                        if (insertData == true) {
                            Toast.makeText(AddCourseToTerm.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                            getAssessmentsToCourse();

                        } else {
                            Toast.makeText(AddCourseToTerm.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(AddCourseToTerm.this, "This term already contains course ID: " +aId, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddCourseToTerm.this, "The course or term ID you entered does not exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getAssessmentsToCourse() {
        String cid = aTermId.getText().toString();
        Cursor cursor = termsCourseJuctionDBH.findData(cid);
        String contactList = "Term " +cid +" Courses"+ "\n";

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(cursor.getColumnIndex("TERMID"));
                String title = cursor.getString(cursor.getColumnIndex("COURSEID"));

                contactList = contactList +"Course ID-" + title + "\n";

            } while (cursor.moveToNext());

        }

        aCourseToTermTextView.setText(contactList);
    }
}