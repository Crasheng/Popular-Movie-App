package com.example.ahmad.popularmovies_final;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.ahmad.popularmovies_final.data.AppDBHelper;
import com.example.ahmad.popularmovies_final.data.MoviesContract;

import java.util.HashSet;

/**
 * Created by Ahmad on 7/25/2015.
 * I got the idea of how test works from SunShine App, so it most like like the test class of sunshine app database
 * feel free to use this code into your project unless your are not Nano-Degree student.
 */
public class DBTest extends AndroidTestCase {

    public static final String LOG_TAG = DBTest.class.getSimpleName();

    void deleteTheDataBase() {
        mContext.deleteDatabase(AppDBHelper.DATABASE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
        deleteTheDataBase();
    }

    public void testCreateDB() throws Throwable {

        final HashSet<String> nameOfTablesInDB = new HashSet<String>();
        nameOfTablesInDB.add(MoviesContract.MoviesEntry.TABLE_NAME);
        nameOfTablesInDB.add(MoviesContract.ReviewsEntry.TABLE_NAME);

        mContext.deleteDatabase(AppDBHelper.DATABASE_NAME);

        SQLiteDatabase db = new AppDBHelper(this.mContext).getWritableDatabase();

        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            nameOfTablesInDB.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain both the movie entry
        // and review entry tables
        assertTrue("Error: Your database was created without both the movie entry and review entry tables",
                nameOfTablesInDB.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + MoviesContract.MoviesEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> movieColumnHashSet = new HashSet<String>();
        movieColumnHashSet.add(MoviesContract.MoviesEntry._ID);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_ID);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_TITLE);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_ORIGINAL_TITLE);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_POSTER);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_BACKDROP);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_OVERVIEW);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_RELEASE_DATE);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_POPULARITY);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_VOTE_COUNTS);
        movieColumnHashSet.add(MoviesContract.MoviesEntry.MOV_COL_VOTE_AVE);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            movieColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                movieColumnHashSet.isEmpty());
        db.close();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


}
