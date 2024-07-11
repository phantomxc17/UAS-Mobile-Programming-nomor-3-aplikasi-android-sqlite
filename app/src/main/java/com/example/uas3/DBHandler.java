package com.example.uas3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "studentsInfo.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NIM = "nim";
    public static final String COLUMN_IPK = "ipk";
    public static final String COLUMN_COURSE = "course";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NIM + " TEXT, " +
                COLUMN_IPK + " REAL, " +
                COLUMN_COURSE + " TEXT)";
        db.execSQL(createTable);

        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, nim, ipk, course) VALUES ('Joshua', '11322456', 3.6, 'Kalkulus 1')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, nim, ipk, course) VALUES ('Yosephine', '11322557', 2.9, 'Dasar Pemrograman 1')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, nim, ipk, course) VALUES ('Geneva', '11321356', 3.1, 'Kalkulus 1')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, nim, ipk, course) VALUES ('Gerald', '11322456', 3.4, 'Analisa Algoritma')");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, nim, ipk, course) VALUES ('Willy', '11321667', 3.5, 'Analisa Algoritma')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addStudent(String name, String nim, double ipk, String course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NIM, nim);
        values.put(COLUMN_IPK, ipk);
        values.put(COLUMN_COURSE, course);
        return db.insert(TABLE_NAME, null, values);
    }

    public int deleteStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}

