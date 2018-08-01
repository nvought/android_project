package c196.com.nathanvought;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SetAlarmForAssessment extends AppCompatActivity {
    EditText assessmentVal;
    AssessmentDBH assessmentDBH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_for_assessment);

        assessmentVal = (EditText) findViewById(R.id.assessmentVal);
        assessmentDBH = new AssessmentDBH(this);
    }
    public void viewHome(View v){
        Intent goHome = new Intent(SetAlarmForAssessment.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backAssessmentHome(View v){
        Intent back = new Intent(SetAlarmForAssessment.this, Assessments.class);
        startActivity(back);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setNotificationTime(View view){
        String id = assessmentVal.getText().toString();
        Cursor data = assessmentDBH.findData(id);

        if (!id.isEmpty()) {


            if (data.getCount() == 0) {
                Toast.makeText(SetAlarmForAssessment.this, "Assessment ID not found", Toast.LENGTH_LONG).show();

            }

            while (data.moveToNext()) {


                try {


                    String dt = data.getString(3);

                    Toast.makeText(SetAlarmForAssessment.this, "Assessment notification set for date:"+dt, Toast.LENGTH_LONG).show();


                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Date startDate = sdf.parse(dt+" 8:00:45");





                    long sdiff = startDate.getTime() - System.currentTimeMillis();
                    long stime = sdiff / 1000;

                    if (stime < 1) {
                        stime = 1;
                    }
                    Long startTime = new GregorianCalendar().getTimeInMillis() + stime * 1000;

                    Intent alertIntent = new Intent(this, AssessmentNotification.class);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, startTime,
                            PendingIntent.getBroadcast(this, 1, alertIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT));


                    SharedPreferences loginData = getSharedPreferences("Assessment", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginData.edit();
                    editor.putString("Title", "You have an assessment due today");
                    editor.putString("Message", "Click to view app");
                    editor.apply();


                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(SetAlarmForAssessment.this, "something wrong with the date", Toast.LENGTH_LONG).show();
                } catch (IllegalArgumentException ie) {
                    ie.printStackTrace();
                    Toast.makeText(SetAlarmForAssessment.this, "something wrong with the sdf", Toast.LENGTH_LONG).show();
                }


            }
        } else{
            Toast.makeText(SetAlarmForAssessment.this, "Please enter in an assessment ID", Toast.LENGTH_LONG).show();
        }
    }
}
