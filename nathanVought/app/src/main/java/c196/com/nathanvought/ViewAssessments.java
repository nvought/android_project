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

public class ViewAssessments extends AppCompatActivity {
    AssessmentDBH assessmentDBH;
    TextView viewAssessments;
    Button viewById;
    EditText byId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessments);
        assessmentDBH = new AssessmentDBH(this);
        viewAssessments = (TextView) findViewById(R.id.viewAssessments);
        viewById = (Button) findViewById(R.id.viewById);
        byId = (EditText) findViewById(R.id.byId);


        viewSingleData();
    }

    public void viewHome(View v){
        Intent goHome = new Intent(ViewAssessments.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backViewAssessment(View v){
        Intent back = new Intent(ViewAssessments.this, Assessments.class);
        startActivity(back);
    }
    public void viewSingleData(){
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = byId.getText().toString();
                Cursor data = assessmentDBH.findData(id);

                if(!id.isEmpty()) {


                    if (data.getCount() == 0) {
                        display("The Assessment ID does not exist", "Please check the ID");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (data.moveToNext()) {
                        buffer.append("ID: " + data.getString(0) + "\n");
                        buffer.append("Title: " + data.getString(1) + "\n");
                        buffer.append("Type: " + data.getString(2) + "\n");
                        buffer.append("Date: " + data.getString(3) + "\n");

                        display("Assessment: ", buffer.toString());
                        getAssessments();
                    }
                }
            }
        });
    }
    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

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

        viewAssessments.setText(contactList);
    }
}
