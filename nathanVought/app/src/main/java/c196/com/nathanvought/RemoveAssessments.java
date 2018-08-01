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

public class RemoveAssessments extends AppCompatActivity {
    AssessmentDBH assessmentDBH;
    EditText deleteId;
    Button removeId;
    TextView assessmentsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_assessments);
        assessmentDBH = new AssessmentDBH(this);
        deleteId = (EditText) findViewById(R.id.deleteId);
        removeId = (Button) findViewById(R.id.removeId);
        assessmentsTextView = (TextView) findViewById(R.id.assessmentsTextView);
        deleteData();
    }

    public void viewHome(View v){
        Intent goHome = new Intent(RemoveAssessments.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backRemoveAssessment(View v){
        Intent back = new Intent(RemoveAssessments.this, Assessments.class);
        startActivity(back);
    }
    public void deleteData() {
        removeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = deleteId.getText().toString();
                if(id.isEmpty()){
                    Toast.makeText(RemoveAssessments.this, "You Must Enter An ID to Delete ", Toast.LENGTH_LONG).show();
                }else {

                    int temp = assessmentDBH.findData(id).getCount();
                    if (temp > 0) {
                        Integer deleteRow = assessmentDBH.deleteData(deleteId.getText().toString());
                        if (deleteRow > 0) {
                            Toast.makeText(RemoveAssessments.this, "Successfully Deleted The Data!", Toast.LENGTH_LONG).show();
                            getAssessments();
                        } else {
                            Toast.makeText(RemoveAssessments.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RemoveAssessments.this, "The ID you entered does not exist", Toast.LENGTH_LONG).show();
                    }
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
