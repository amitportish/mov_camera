/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mov.android.camera2video.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(umov_bitsDbHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /*
        Students: Uncomment this test once you've written the code to create the Location
        table.  Note that you will have to have chosen the same column names that I did in
        my solution for this test to compile, so if you haven't yet done that, this is
        a good time to change your column names to match mine.

        Note that this only tests that the Location table has the correct columns, since we
        give you the code for the weather table.  This test does not look at the
     */
    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();

        tableNameHashSet.add(umov_bitsContract.BitsEntry.TABLE_NAME);

        mContext.deleteDatabase(umov_bitsDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new umov_bitsDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + umov_bitsContract.BitsEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> locationColumnHashSet = new HashSet<String>();
        locationColumnHashSet.add(umov_bitsContract.BitsEntry._ID);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_NAME);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_SHORT_DESC);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_TYPE);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_DURATION);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_CAMERA_LAYOUT);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_CAMERA_DIRECTION);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_TEXT_INSTRUCTION);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_VIDEO_INSTRUCTION_URI);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_KARAOKE_AUDIO_URI);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_KARAOKE_LYRICS);
        locationColumnHashSet.add(umov_bitsContract.BitsEntry.COLUMN_AUDIO_INSTRUCTION_URI);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            locationColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                locationColumnHashSet.isEmpty());
        db.close();
    }


    /*
        Students:  Here is where you will build code to test that we can insert and query the
        database.  We've done a lot of work for you.  You'll want to look in TestUtilities
        where you can use the "createWeatherValues" function.  You can
        also make use of the validateCurrentRecord function from within TestUtilities.
     */
    public void testUMovBitsTable() {

        // First step: Get reference to writable database
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        umov_bitsDbHelper dbHelper = new umov_bitsDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step (Weather): Create weather values
        ContentValues bitsValues = TestUtilities.createUMovBitValues();

        // Third Step (Weather): Insert ContentValues into database and get a row ID back
        long weatherRowId = db.insert(umov_bitsContract.BitsEntry.TABLE_NAME, null, bitsValues);
        assertTrue(weatherRowId != -1);

        // Fourth Step: Query the database and receive a Cursor back
        // A cursor is your primary interface to the query results.
        Cursor bitsCursor = db.query(
                umov_bitsContract.BitsEntry.TABLE_NAME,  // Table to Query
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );

        // Move the cursor to the first valid database row and check to see if we have any rows
        assertTrue("Error: No Records returned from location query", bitsCursor.moveToFirst());

        // Fifth Step: Validate the location Query
        TestUtilities.validateCurrentRecord("testInsertReadDb weatherEntry failed to validate",
                bitsCursor, bitsValues);

        // Move the cursor to demonstrate that there is only one record in the database
        assertFalse("Error: More than one record returned from weather query",
                bitsCursor.moveToNext());

        // Sixth Step: Close cursor and database
        bitsCursor.close();
        dbHelper.close();
    }

}