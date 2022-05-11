package com.example.blogapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import android.database.Cursor;
public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "onestoplocal";

    // below int is our database version
    private static final int DB_VERSION = 1;

    private SQLiteDatabase sqLiteDatabase;
    // below variable is for our table name.
    private static final String TABLE_ONESTOP = "offline_blogs";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String TITLE = "title";

    // below variable id for our course duration column.
    private static final String DESCRIPTION = "description";

    // below variable for our course description column.
    private static final String  IMAGELINK = "imagelink";

    // below variable is for our course tracks column.
    private static final String THUMBNAILLINK = "thumbnaillink";

    private String LOGTAG = "DBHandler";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {


        super(context, DB_NAME, null, 1);
        Log.e(LOGTAG, "creating database");

    }

    public void createTable(){
        Log.e("CreatNewTable", "Creating table" + TABLE_ONESTOP);
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("CreatNewTable", "Creating table" + TABLE_ONESTOP);
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_ONESTOP + " ("
                + ID_COL + " TEXT PRIMARY KEY, "
                + TITLE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + IMAGELINK + " TEXT,"
                + THUMBNAILLINK + " TEXT)";

        db.execSQL(query);
        Log.e("CreatNewTable", "Creating table" + TABLE_ONESTOP);

    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {



    }

    public ArrayList<Model> getAllBlogs() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("read from db","red");

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_ONESTOP
                , null);

        // on below line we are creating a new array list.
        ArrayList<Model> courseModalArrayList = new ArrayList<Model>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModalArrayList.add(new Model(cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }


    public void addBlog(String[] inValues) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        Log.e("CreatNewTable", "add blog table" + TABLE_ONESTOP);
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.

        values.put(ID_COL, inValues[0]);
        values.put(TITLE, inValues[1]);
        values.put(DESCRIPTION, inValues[2]);
        values.put(IMAGELINK, inValues[3]);
        values.put(THUMBNAILLINK, inValues[4]);
        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_ONESTOP, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
        Log.e("CreatNewTable", "add blog table" + TABLE_ONESTOP);
    }


    public void deleteBlog(String blogId) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        Log.e("CreatNewTable", "delete blog table" + TABLE_ONESTOP);
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.


        // on below line we are passing all values
        // along with its key and value pair.


        // content values to our table.
        db.delete(TABLE_ONESTOP, "id=?", new String[]{blogId});

        // at last we are closing our
        // database after adding database.
        db.close();
        Log.e("CreatNewTable", "add blog table" + TABLE_ONESTOP);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ONESTOP );
        onCreate(db);
    }
}
