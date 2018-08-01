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

public class EditAssessments extends AppCompatActivity {
    AssessmentDBH assessmentDBH;
    Button editAssessment;
    TextView assessmentsTextView;
    EditText editAssessmentId,editAssessmentTitle, editAssessmentType, editDueDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessments);

        assessmentDBH = new AssessmentDBH(this);
        editAssessment = (Button) findViewById(R.id.editAssessment);
        assessmentsTextView = (TextView) findViewById(R.id.assessmentsTextView);
        editAssessmentId = (EditText) findViewById(R.id.editAssessmentId);
        editAssessmentTitle = (EditText) findViewById(R.id.editAssessmentTitle);
        editAssessmentType = (EditText) findViewById(R.id.editAssessmentType);
        editDueDate = (EditText) findViewById(R.id.editDueDate);

        getAssessments();
        updateData();


    }

    public void viewHome(View v){
        Intent goHome = new Intent(EditAssessments.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backEditAssessment(View v){
        Intent back = new Intent(EditAssessments.this, Assessments.class);
        startActivity(back);
    }
    public void updateData(){
        editAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editAssessmentId.getText().toString();
                String title = editAssessmentTitle.getText().toString();
                String type = editAssessmentType.getText().toString();
                String date = editDueDate.getText().toString();

                if(!id.isEmpty() && !title.isEmpty() && !type.isEmpty() && !date.isEmpty()) {
                    if (date.matches("\\d{2}-\\d{2}-\\d{4}")) {
                        int temp = assessmentDBH.findData(id).getCount();
                        if (temp > 0) {
                            boolean update = assessmentDBH.updateData(editAssessmentId.getText().toString(), editAssessmentTitle.getText().toString(),
                                    editAssessmentType.getText().toString(), editDueDate.getText().toString());
                            if (update == true) {
                                Toast.makeText(EditAssessments.this, "Successfully Updated Data!", Toast.LENGTH_LONG).show();
                                getAssessments();
                            } else {
                                Toast.makeText(EditAssessments.this, "Something Went Wrong :(.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(EditAssessments.this, "The Assessment ID does not exist", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(EditAssessments.this, "Date must be MM-DD-YYYY example 12-17-2017", Toast.LENGTH_LONG).show();
                    }
                }
                    else{
                    Toast.makeText(EditAssessments.this, "Missing information", Toast.LENGTH_LONG).show();
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
