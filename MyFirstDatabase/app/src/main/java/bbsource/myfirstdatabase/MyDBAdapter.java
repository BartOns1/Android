package bbsource.myfirstdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vdabcursist on 05/10/2017.
 */

public class MyDBAdapter {
    private static final String DATABASE_NAME = "data";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private final MyDBHelper mDPHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyDBAdapter(Context context) {
        this.context=context;
        mDPHelper = new MyDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        sqLiteDatabase = mDPHelper.getWritableDatabase();
    }

    public void insertStudent(String name, int faculty) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("faculty", faculty);
        sqLiteDatabase.insert("students", null, cv);
    }

    public List<String> selectAllStudents(){
        List<String> allstudents = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query("students", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            do {
                allstudents.add(cursor.getString(1));
            }
            while (cursor.moveToNext());
        }
        return allstudents;
    }

    public void deleteAllEngineers(){
        sqLiteDatabase.delete("students", null, null);
    }



    public class MyDBHelper extends SQLiteOpenHelper {
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            String query = "CREATE TABLE students(id integer primary key autoincrement, name text, faculty integer);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            String query="DROP TABLE IF EXISTS students;"; //niet ok voor updating in google play store
            db.execSQL(query);
            onCreate(db);
        }
    }
}
