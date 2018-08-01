package c196.com.nathanvought;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mac on 10/28/17.
 */

public class AddAssessments extends AppCompatActivity {
    TextView assessmentsTextView = null;
    Button buttonAddAssessment;
    EditText assessmentTitle, assessmentType, dueDate;
    DatePicker assessmentDueDate;
    AssessmentDBH assessmentDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessments);

        assessmentsTextView = (TextView) findViewById(R.id.assessmentsTextView);
        assessmentTitle = (EditText) findViewById(R.id.assessmentTitle);
        assessmentType = (EditText) findViewById(R.id.assessmentType);
        dueDate = (EditText) findViewById(R.id.dueDate);
        buttonAddAssessment = (Button) findViewById(R.id.addAssessment);

        assessmentDBH = new AssessmentDBH(this);

        addAssessment();
        getAssessments();

    }
    public void viewHome(View v){
        Intent goHome = new Intent(AddAssessments.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backAssessment(View v){
        Intent back = new Intent(AddAssessments.this, Assessments.class);
        startActivity(back);
    }

    public void addAssessment() {
        buttonAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = assessmentTitle.getText().toString();
                String type = assessmentType.getText().toString();
                String date = dueDate.getText().toString();

                if(!title.isEmpty() && !type.isEmpty() && !date.isEmpty()) {
                    if (date.matches("\\d{2}-\\d{2}-\\d{4}")) {


                        boolean insertData = assessmentDBH.addData(title, type, date);

                        if (insertData == true) {
                            Toast.makeText(AddAssessments.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                            getAssessments();

                        } else {
                            Toast.makeText(AddAssessments.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AddAssessments.this, "Incorrect Date format", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(AddAssessments.this, "Missing info", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void getAssessments() {
        Cursor cursor = assessmentDBH.showData();
        String contactList = "";

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(cursor.getColumnIndex("ID"));
                String title = cursor.getString(cursor.getColumnIndex("TITLE"));

                contactList = contactList + id + "  :  " + title + "\n";

            } while (cursor.moveToNext());

        }

        assessmentsTextView.setText(contactList);
    }

}
