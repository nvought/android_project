package c196.com.nathanvought;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Progress extends AppCompatActivity {

    GridLayout progressLayout;
    CourseDBH courseDBH;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        progressLayout = (GridLayout) findViewById(R.id.progressLayout);
        courseDBH = new CourseDBH(this);
        findProgress();


    }
    public void viewHome(View v){
        Intent goHome = new Intent(Progress.this, MainActivity.class);
        startActivity(goHome);
    }

    public void findProgress(){

        int totalCount = courseDBH.countTotal();
        int totalIP = courseDBH.countInProgress();
        int totalC = courseDBH.countCompleted();
        int totalPT = courseDBH.countPlanToTake();
        int totalD = courseDBH.countDropped();

        double ipCalc = ((1.0 *totalIP)/(1.0*totalCount))*100;
        String ipPercent = String.valueOf(Math.round(ipCalc))+"%";

        double cCalc = ((1.0 *totalC) / (1.0*totalCount)) * 100;
        String cPercent = String.valueOf(Math.round(cCalc))+"%";

        double ptCalc = ((1.0 *totalPT) /(1.0*totalCount)) * 100;
        String ptPercent = String.valueOf(Math.round(ptCalc))+"%";

        double dCalc = ((1.0 *totalD) /(1.0*totalCount)) * 100;
        String dPercent = String.valueOf(Math.round(dCalc))+"%";




        TextView title = new TextView(this);
        title.setText("Progress Tracking");
        float size = 30;
        title.setPadding(0,0,0,0);
        title.setTextSize(size);
        title.setWidth(2000);
        title.setGravity(Gravity.CENTER);
        progressLayout.addView(title);


        TextView info = new TextView(this);
        info.setText("Total Courses");
        float size2 = 20;
        info.setPadding(0,0,0,0);
        info.setTextSize(size2);
        info.setTextColor(Color.BLACK);
        info.setPadding(0,0,0,0);
        info.setWidth(2000);
        info.setGravity(Gravity.CENTER);
        progressLayout.addView(info);

        TextView total = new TextView(this);
        total.setText(String.valueOf(totalCount));
        float size3 = 40;
        total.setPadding(0,0,0,0);
        total.setTextSize(size3);
        total.setTextColor(Color.BLACK);
        total.setWidth(2000);
        total.setGravity(Gravity.CENTER);
        progressLayout.addView(total);

        TextView ip = new TextView(this);
        ip.setText("Total In Progress");
        ip.setPadding(0,0,0,0);
        ip.setTextSize(20);
        ip.setTextColor(Color.GREEN);
        ip.setWidth(2000);
        ip.setGravity(Gravity.CENTER);
        progressLayout.addView(ip);

        TextView totIP = new TextView(this);
        totIP.setText(String.valueOf(totalIP + "\n"+ipPercent));
        totIP.setPadding(0,0,0,0);
        totIP.setTextSize(40);
        totIP.setTextColor(Color.GREEN);
        totIP.setWidth(2000);
        totIP.setGravity(Gravity.CENTER);
        progressLayout.addView(totIP);

        TextView complete = new TextView(this);
        complete.setText("Total Complete");
        complete.setPadding(0,0,0,0);
        complete.setTextSize(20);
        complete.setTextColor(Color.BLUE);
        complete.setWidth(2000);
        complete.setGravity(Gravity.CENTER);
        progressLayout.addView(complete);

        TextView totcomp = new TextView(this);
        totcomp.setText(String.valueOf(totalC + "\n"+cPercent));
        totcomp.setPadding(0,0,0,0);
        totcomp.setTextSize(40);
        totcomp.setTextColor(Color.BLUE);
        totcomp.setWidth(2000);
        totcomp.setGravity(Gravity.CENTER);
        progressLayout.addView(totcomp);

        TextView plan = new TextView(this);
        plan.setText("Total Plan to Take");
        plan.setPadding(0,0,0,0);
        plan.setTextSize(20);
        plan.setTextColor(Color.DKGRAY);
        plan.setWidth(2000);
        plan.setGravity(Gravity.CENTER);
        progressLayout.addView(plan);

        TextView totplan = new TextView(this);
        totplan.setText(String.valueOf(totalPT + "\n"+ ptPercent));
        totplan.setPadding(0,0,0,0);
        totplan.setTextSize(40);
        totplan.setTextColor(Color.DKGRAY);
        totplan.setWidth(2000);
        totplan.setGravity(Gravity.CENTER);
        progressLayout.addView(totplan);

        TextView drop = new TextView(this);
        drop.setText("Total Dropped");
        drop.setPadding(0,0,0,0);
        drop.setTextSize(20);
        drop.setTextColor(Color.RED);
        drop.setWidth(2000);
        drop.setGravity(Gravity.CENTER);
        progressLayout.addView(drop);

        TextView totdrop = new TextView(this);
        totdrop.setText(String.valueOf(totalD + "\n"+ dPercent));
        totdrop.setPadding(0,0,0,0);
        totdrop.setTextSize(40);
        totdrop.setTextColor(Color.RED);
        totdrop.setWidth(2000);
        totdrop.setGravity(Gravity.CENTER);
        progressLayout.addView(totdrop);




    }
}
