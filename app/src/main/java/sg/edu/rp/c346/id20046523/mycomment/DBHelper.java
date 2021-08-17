package sg.edu.rp.c346.id20046523.mycomment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "showlist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SHOWS = "shows";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_VOICE = "voice";
    private static final String COLUMN_YORELEASED = "yor";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createShowsTableSql = "CREATE TABLE " + TABLE_SHOWS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_VOICE + " TEXT, "
                + COLUMN_YORELEASED + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";

        db.execSQL(createShowsTableSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOWS);
        onCreate(db);
    }

    public long insertShow(String name, int yor, int stars, String voice)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YORELEASED, yor);
        values.put(COLUMN_STARS, stars);
        values.put(COLUMN_VOICE, voice);

        long result = db.insert(TABLE_SHOWS, null, values);
        db.close();
        return result;
    }

    public ArrayList<Show> getAllShows()
    {
        ArrayList<Show> showsList = new ArrayList<Show>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_YORELEASED + ","
                + COLUMN_STARS + ","
                + COLUMN_VOICE + " FROM " + TABLE_SHOWS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int yor = cursor.getInt(2);
                int stars = cursor.getInt(3);
                String voice = cursor.getString(4);

                Show newShow = new Show(id, name, yor, stars, voice);
                showsList.add(newShow);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return showsList;
    }

    public ArrayList<Show> getAllShowsByStars(int starsFilter) {
        ArrayList<Show> showsList = new ArrayList<Show>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_YORELEASED, COLUMN_STARS, COLUMN_VOICE};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor = db.query(TABLE_SHOWS, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int yor = cursor.getInt(2);
                int stars = cursor.getInt(3);
                String voice = cursor.getString(4);

                Show newShow = new Show(id, name, yor, stars, voice);
                showsList.add(newShow);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return showsList;
    }

    public int updateShow(Show data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_YORELEASED, data.getYor());
        values.put(COLUMN_STARS, data.getStars());
        values.put(COLUMN_VOICE, data.getVoice());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SHOWS, values, condition, args);
        db.close();
        return result;
    }

    public int deleteShow(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SHOWS, condition, args);
        db.close();
        return result;
    }

    public ArrayList<String> getYears() {
        ArrayList<String> codes = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_YORELEASED};

        Cursor cursor;
        cursor = db.query(true, TABLE_SHOWS, columns, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                codes.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return codes;
    }

    public ArrayList<Show> getAllShowsByYear(int yearFilter) {
        ArrayList<Show> showslist = new ArrayList<Show>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_YORELEASED, COLUMN_STARS, COLUMN_VOICE};
        String condition = COLUMN_YORELEASED + "= ?";
        String[] args = {String.valueOf(yearFilter)};

        Cursor cursor = db.query(TABLE_SHOWS, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int yor = cursor.getInt(2);
                int stars = cursor.getInt(3);
                String voice = cursor.getString(4);

                Show newShow = new Show(id, name, yor, stars, voice);
                showslist.add(newShow);
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();
        return showslist;
    }

}
