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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SetAlarmForCourse extends AppCompatActivity {
    Button btnSet;
    EditText etVal;
    CourseDBH courseDBH;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm_for_course);
        btnSet = (Button) findViewById(R.id.btnSetAlarm);
        etVal = (EditText) findViewById(R.id.etVal);
        courseDBH = new CourseDBH(this);

    }
    public void viewHome(View v){
        Intent goHome = new Intent(SetAlarmForCourse.this, MainActivity.class);
        startActivity(goHome);
    }
    public void backCourses(View v){
        Intent back = new Intent(SetAlarmForCourse.this, Courses.class);
        startActivity(back);
    }



        @RequiresApi(api = Build.VERSION_CODES.M)
        public void setNotificationTime(View view){
            String id = etVal.getText().toString();
            Cursor data = courseDBH.findData(id);

            if (!id.isEmpty()) {


                if (data.getCount() == 0) {
                    Toast.makeText(SetAlarmForCourse.this, "Course ID not found", Toast.LENGTH_LONG).show();

                }

                while (data.moveToNext()) {


                    try {



                        String sd = data.getString(2);
                        String ed = data.getString(3);

                        Toast.makeText(SetAlarmForCourse.this, "Course notification set for start date:"+sd +" and end date: "+ ed, Toast.LENGTH_LONG).show();


                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        Date startDate = sdf.parse(sd+" 8:00:00");
                        Date endDate = sdf.parse(ed+" 8:00:30");




                        long sdiff = startDate.getTime() - System.currentTimeMillis();
                        long stime = sdiff / 1000;

                        if (stime < 1) {
                            stime = 1;
                        }
                        Long startTime = new GregorianCalendar().getTimeInMillis() + stime * 1000;

                        Intent alertIntent = new Intent(this, StartDateNotification.class);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, startTime,
                                PendingIntent.getBroadcast(this, 1, alertIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT));


                        SharedPreferences loginData = getSharedPreferences("StartDate", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = loginData.edit();
                        editor.putString("sdTitle", "You have a course starting today");
                        editor.putString("sdMessage", "Click to view app");
                        editor.apply();


                        long ediff = endDate.getTime() - System.currentTimeMillis();
                        long etime = ediff / 1000;

                        if (etime < 1) {
                            etime = 1;
                        }
                        Long endTime = new GregorianCalendar().getTimeInMillis() + etime * 1000;

                        Intent alertIntentED = new Intent(this, CourseEndDateNotification.class);
                        AlarmManager alarmManagerED = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(alarmManagerED.RTC_WAKEUP, endTime,
                                PendingIntent.getBroadcast(this, 1, alertIntentED,
                                        PendingIntent.FLAG_UPDATE_CURRENT));


                        SharedPreferences loginDataED = getSharedPreferences("EndDate", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorED = loginDataED.edit();
                        editorED.putString("edTitle", "You have a course ending today");
                        editorED.putString("edMessage", "Click to view app");
                        editorED.apply();


                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(SetAlarmForCourse.this, "something wrong with the date", Toast.LENGTH_LONG).show();
                    } catch (IllegalArgumentException ie) {
                        ie.printStackTrace();
                        Toast.makeText(SetAlarmForCourse.this, "something wrong with the sdf", Toast.LENGTH_LONG).show();
                    }


                }
            } else{
                Toast.makeText(SetAlarmForCourse.this, "Please enter in a course ID", Toast.LENGTH_LONG).show();
            }
        }




}
