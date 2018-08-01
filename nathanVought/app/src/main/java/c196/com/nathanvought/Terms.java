package c196.com.nathanvought;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by mac on 10/28/17.
 */

public class Terms extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }

    public void viewHome(View v){
        Intent goHome = new Intent(Terms.this, MainActivity.class);
        startActivity(goHome);
    }
    public void viewTerms(View v) {
        Intent goViewTerms = new Intent(Terms.this, ViewTerms.class);
        startActivity(goViewTerms);
    }
    public void viewAddTerms(View v) {
        Intent goViewAddTerms = new Intent(Terms.this, AddTerms.class);
        startActivity(goViewAddTerms);
    }
    public void viewEditTerms(View v) {
        Intent goViewEditTerms = new Intent(Terms.this, EditTerms.class);
        startActivity(goViewEditTerms);
    }
    public void viewRemoveTerms(View v) {
        Intent goViewRemoveTerms = new Intent(Terms.this, RemoveTerms.class);
        startActivity(goViewRemoveTerms);
    }
    public void viewAddCourseToTerms(View v) {
        Intent goViewRemoveTerms = new Intent(Terms.this, AddCourseToTerm.class);
        startActivity(goViewRemoveTerms);
    }
    public void viewRemoveCourseFromTerms(View v) {
        Intent goViewRemoveTerms = new Intent(Terms.this, RemoveCourseFromTerm.class);
        startActivity(goViewRemoveTerms);
    }
}
