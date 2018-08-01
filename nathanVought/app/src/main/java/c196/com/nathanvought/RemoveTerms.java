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


public class RemoveTerms extends AppCompatActivity {
    TermsDBH termsDBH;
    TermsCourseJuctionDBH termsCourseJuctionDBH;
    EditText deleteTermId;
    Button removeTermId;
    TextView removeTermTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_terms);
        termsDBH = new TermsDBH(this);
        termsCourseJuctionDBH = new TermsCourseJuctionDBH(this);
        deleteTermId = (EditText) findViewById(R.id.deleteTermId);
        removeTermId = (Button) findViewById(R.id.removeTermId);
        removeTermTextView = (TextView) findViewById(R.id.removeTermTextView);
        deleteData();
    }

    public void viewHome(View v) {
        Intent goHome = new Intent(RemoveTerms.this, MainActivity.class);
        startActivity(goHome);
    }

    public void backRemoveTerms(View v) {
        Intent back = new Intent(RemoveTerms.this, Terms.class);
        startActivity(back);
    }

    public void deleteData() {
        removeTermId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = deleteTermId.getText().toString().length();
                Cursor courseTermData = termsCourseJuctionDBH.findData(deleteTermId.getText().toString());
                int termHasCourse = courseTermData.getCount();
                if (termHasCourse == 0) {

                    if (temp > 0) {
                        Integer deleteRow = termsDBH.deleteData(deleteTermId.getText().toString());
                        if (deleteRow > 0) {
                            Toast.makeText(RemoveTerms.this, "Successfully Deleted The Data!", Toast.LENGTH_LONG).show();
                            getCourses();
                        } else {
                            Toast.makeText(RemoveTerms.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RemoveTerms.this, "You Must Enter An ID to Delete :(.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RemoveTerms.this, "You cannot delete this term because it has a course. Please remove any courses to delete this term.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getCourses() {
        Cursor cursor = termsDBH.showData();
        String contactList = "";

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(cursor.getColumnIndex("ID"));
                String title = cursor.getString(cursor.getColumnIndex("TITLE"));

                contactList = contactList + id + "  :  " + title + "\n";

            } while (cursor.moveToNext());

        }

        removeTermTextView.setText(contactList);
    }
}