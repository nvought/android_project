package c196.com.nathanvought;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mac on 12/2/17.
 */

public class TermsCourseJuctionDBH extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "c196TermCourseJunction.db";
    public static final String TABLE_NAME = "Term_Course_junction_table";
    public static final String COL2 = "TERMID";
    public static final String COL3 = "COURSEID";

    public TermsCourseJuctionDBH(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                "( TERMID TEXT, COURSEID TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addData(String courseid, String assessmentid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,courseid);
        contentValues.put(COL3,assessmentid);


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
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COL2 +" ="+id, null);
        return data;
    }

    public boolean updateData(String courseid, String assessmentid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,courseid);
        contentValues.put(COL3,assessmentid);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {courseid});
        return true;
    }

    public Cursor deleteData(String cid, String aid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("DELETE FROM " + TABLE_NAME + " WHERE "+COL2 +" ="+cid+ " AND "+COL3 +" ="+aid, null);
        return data;
    }

    public Cursor findCourseAndAssessmentMatch(String cid, String aid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COL2 +" ="+cid+ " AND "+COL3 +" ="+aid, null);
        return data;
    }

}