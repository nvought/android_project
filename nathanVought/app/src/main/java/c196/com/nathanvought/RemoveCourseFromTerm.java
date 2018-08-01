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

public class RemoveCourseFromTerm extends AppCompatActivity {

    TermsCourseJuctionDBH termsCourseJuctionDBH;
    TermsDBH termsDBH;
    CourseDBH courseDBH;
    EditText dcTermId, dcCourseId;
    Button deleteCourseFromTerm;
    TextView dCourseFromTermTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_course_from_term);

        termsCourseJuctionDBH = new TermsCourseJuctionDBH(this);
        termsDBH = new TermsDBH(this);
        courseDBH = new CourseDBH(this);
        dcTermId = (EditText) findViewById(R.id.dcTermId);
        dcCourseId = (EditText) findViewById(R.id.dcCourseId);
        deleteCourseFromTerm = (Button) findViewById(R.id.deleteCourseFromTerm);
        dCourseFromTermTextView = (TextView) findViewById(R.id.dCourseFromTermTextView);

        deleteCourseFromTerm();
    }
    public void viewHome(View v){
        Intent goHome = new Intent(RemoveCourseFromTerm.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backAddTerms(View v){
        Intent back = new Intent(RemoveCourseFromTerm.this, Terms.class);
        startActivity(back);
    }
    public void deleteCourseFromTerm() {
        deleteCourseFromTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cId = dcTermId.getText().toString();
                String aId = dcCourseId.getText().toString();

                Cursor courseData = termsDBH.findData(cId);
                int courseExists = courseData.getCount();
                Cursor assessmentData = courseDBH.findData(aId);
                int assessmentExists = assessmentData.getCount();



                if (courseExists != 0 && assessmentExists !=0) {

                    Cursor findData = termsCourseJuctionDBH.findCourseAndAssessmentMatch(cId,aId);
                    int matchFound = findData.getCount();

                    if(matchFound!=0) {

                        Cursor deleteData = termsCourseJuctionDBH.deleteData(cId, aId);
                        int deleteSuccessful = deleteData.getCount();

                        if (deleteSuccessful == 0) {
                            Toast.makeText(RemoveCourseFromTerm.this, "Data Successfully Deleted!", Toast.LENGTH_LONG).show();
                            getCourseToTerm();

                        } else {
                            Toast.makeText(RemoveCourseFromTerm.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(RemoveCourseFromTerm.this, "This course does not contain assessment ID:"+aId, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RemoveCourseFromTerm.this, "The course or assessment ID you entered does not exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getCourseToTerm() {
        String cid = dcTermId.getText().toString();
        Cursor cursor = termsCourseJuctionDBH.findData(cid);
        String contactList = "Term " +cid +" Courses"+ "\n";

        if (cursor.moveToFirst()) {

            do {


                String title = cursor.getString(cursor.getColumnIndex("COURSEID"));

                contactList = contactList +"Course ID-" + title + "\n";

            } while (cursor.moveToNext());

        }

        dCourseFromTermTextView.setText(contactList);
    }
}
