package c196.com.nathanvought;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mac on 12/2/17.
 */

public class AssessmentDBH extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "c196.db";
    public static final String TABLE_NAME = "assessments_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "TITLE";
    public static final String COL3 = "TYPE";
    public static final String COL4 = "DATE";

    public AssessmentDBH(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " TITLE TEXT, TYPE TEXT, DATE TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    public boolean addData(String title, String type, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,title);
        contentValues.put(COL3,type);
        contentValues.put(COL4, date);

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

    public boolean updateData(String id, String title, String type, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,id);
        contentValues.put(COL2,title);
        contentValues.put(COL3,type);
        contentValues.put(COL4,date);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

}