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

public class EditTerms extends AppCompatActivity {
    TermsDBH termsDBH;
    EditText editTermId,edtTermTitle, editTermsStartDate, editTermsEndDate;
    TextView editTermsTextView;
    Button editTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_terms);

        termsDBH = new TermsDBH(this);

        editTermId = (EditText) findViewById(R.id.editTermId);
        edtTermTitle = (EditText) findViewById(R.id.edtTermTitle);
        editTermsStartDate = (EditText) findViewById(R.id.editTermsStartDate);
        editTermsEndDate = (EditText) findViewById(R.id.editTermsEndDate);
        editTermsTextView = (TextView) findViewById(R.id.editTermsTextView);
        editTerms = (Button) findViewById(R.id.editTerms);
        editTerm();
    }

    public void viewHome(View v){
        Intent goHome = new Intent(EditTerms.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backEditTerms(View v){
        Intent back = new Intent(EditTerms.this, Terms.class);
        startActivity(back);
    }

    public void editTerm() {
        editTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = editTermId.getText().toString();
                String title = edtTermTitle.getText().toString();
                String sdate = editTermsStartDate.getText().toString();
                String edate = editTermsEndDate.getText().toString();
                int termExists = termsDBH.findData(id).getCount();

                if(!id.isEmpty() && !title.isEmpty() && !sdate.isEmpty() && !edate.isEmpty()) {
                    if (sdate.matches("\\d{2}-\\d{2}-\\d{4}") && edate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                       if(termExists > 0) {
                           boolean insertData = termsDBH.updateData(id, title, sdate, edate);

                           if (insertData == true) {
                               Toast.makeText(EditTerms.this, "Data Successfully Updated!", Toast.LENGTH_LONG).show();
                               getTerms();

                           } else {
                               Toast.makeText(EditTerms.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                           }
                       } else{
                           Toast.makeText(EditTerms.this, "Term ID does not exist", Toast.LENGTH_LONG).show();

                       }
                    } else {
                        Toast.makeText(EditTerms.this, "Incorrect date format.", Toast.LENGTH_LONG).show();

                    }
                } else{
                    Toast.makeText(EditTerms.this, "Missing info", Toast.LENGTH_LONG).show();

                }
            }
        });
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

        editTermsTextView.setText(contactList);
    }
}
