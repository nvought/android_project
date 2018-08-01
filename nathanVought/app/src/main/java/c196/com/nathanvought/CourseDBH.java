package c196.com.nathanvought;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by mac on 12/2/17.
 */

public class CourseDBH extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "c196Course.db";
    public static final String TABLE_NAME = "course_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "TITLE";
    public static final String COL3 = "SDATE";
    public static final String COL4 = "EDATE";
    public static final String COL5 = "STATUS";
    public static final String COL6 = "CMNAME";
    public static final String COL7 = "CMEMAIL";
    public static final String COL8 = "CMPHONE";

    public CourseDBH(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " TITLE TEXT, SDATE TEXT, EDATE TEXT, STATUS TEXT, CMNAME TEXT, CMEMAIL TEXT, CMPHONE TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addData(String title, String sdate, String edate,String status, String cmname, String cmemail, String cmphone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,title);
        contentValues.put(COL3,sdate);
        contentValues.put(COL4,edate);
        contentValues.put(COL5,status);
        contentValues.put(COL6,cmname);
        contentValues.put(COL7,cmemail);
        contentValues.put(COL8,cmphone);

        long result  = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public Cursor findData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COL1 +" ="+id, null);
        return data;
    }

    public boolean updateData(String id, String title, String sdate, String edate, String status, String cmname, String cmemail, String cmphone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,title);
        contentValues.put(COL3,sdate);
        contentValues.put(COL4,edate);
        contentValues.put(COL5,status);
        contentValues.put(COL6,cmname);
        contentValues.put(COL7,cmemail);
        contentValues.put(COL8,cmphone);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }
    public int countTotal(){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null).getCount();
    }

    public int countInProgress(){
        SQLiteDatabase db = this.getWritableDatabase();
        String ip = "In Progress";
        Cursor data  = db.rawQuery("select * from course_table where STATUS='"+ip+"'",null);
        int count = data.getCount();

        if(count<1){
            count = 0;
        }

        return count;
    }
    public int countCompleted(){
        SQLiteDatabase db = this.getWritableDatabase();
        String c = "Completed";
        Cursor data  = db.rawQuery("select * from course_table where STATUS='"+c+"'",null);
        int count = data.getCount();

        if(count<1){
            count = 0;
        }

        return count;
    }
    public int countPlanToTake(){
        SQLiteDatabase db = this.getWritableDatabase();
        String pt = "Plan to Take";
        Cursor data  = db.rawQuery("select * from course_table where STATUS='"+pt+"'",null);
        int count = data.getCount();

        if(count<1){
            count = 0;
        }

        return count;
    }
    public int countDropped(){
        SQLiteDatabase db = this.getWritableDatabase();
        String d = "Dropped";
        Cursor data  = db.rawQuery("select * from course_table where STATUS='"+d+"'",null);
        int count = data.getCount();

        if(count<1){
            count = 0;
        }

        return count;
    }



}