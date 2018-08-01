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

public class AddTerms extends AppCompatActivity {

    TermsDBH termsDBH;
    EditText addTermTitle, addTermsStartDate, addTermsEndDate;
    TextView addTermsTextView;
    Button addTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terms);

        termsDBH = new TermsDBH(this);

        addTermTitle = (EditText) findViewById(R.id.addTermTitle);
        addTermsStartDate = (EditText) findViewById(R.id.addTermsStartDate);
        addTermsEndDate = (EditText) findViewById(R.id.addTermsEndDate);
        addTerms = (Button) findViewById(R.id.addTerms);
        addTermsTextView = (TextView) findViewById(R.id.addTermsTextView);
        addTerm();
    }

    public void viewHome(View v){
        Intent goHome = new Intent(AddTerms.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backAddTerms(View v){
        Intent back = new Intent(AddTerms.this, Terms.class);
        startActivity(back);
    }
    public void addTerm() {
        addTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = addTermTitle.getText().toString();
                String sdate = addTermsStartDate.getText().toString();
                String edate = addTermsEndDate.getText().toString();

               if(!title.isEmpty() && !sdate.isEmpty() && !edate.isEmpty()) {
                   if (sdate.matches("\\d{2}-\\d{2}-\\d{4}") && edate.matches("\\d{2}-\\d{2}-\\d{4}")) {


                       boolean insertData = termsDBH.addData(title, sdate, edate);

                       if (insertData == true) {
                           Toast.makeText(AddTerms.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                           getTerms();

                       } else {
                           Toast.makeText(AddTerms.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                       }
                   }else{
                       Toast.makeText(AddTerms.this, "Incorrect Date format", Toast.LENGTH_LONG).show();

                   }

               }else{
                   Toast.makeText(AddTerms.this, "Missing Info", Toast.LENGTH_LONG).show();

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

        addTermsTextView.setText(contactList);
    }
}
