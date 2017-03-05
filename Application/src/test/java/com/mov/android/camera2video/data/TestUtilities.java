package com.mov.android.camera2video.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;


import java.util.Map;
import java.util.Set;

/*
    Students: These are functions and some test data to make it easier to test your database and
    Content Provider.  Note that you'll want your WeatherContract class to exactly match the one
    in our solution to use these as-given.
 */
public class TestUtilities extends AndroidTestCase {

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    /*
        Students: Use this to create some default weather values for your database tests.
     */
    static ContentValues createUMovBitValues() {
        ContentValues bitValues = new ContentValues();
        bitValues.put(umov_bitsContract.BitsEntry.COLUMN_NAME, "hello");
        bitValues.put(umov_bitsContract.BitsEntry.COLUMN_SHORT_DESC, "Welcome Greeting");
        bitValues.put(umov_bitsContract.BitsEntry.COLUMN_TYPE, umov_bitsContract.BitsEntry.TEXT_INSTRUCTION_TYPE);
        bitValues.put(umov_bitsContract.BitsEntry.COLUMN_CAMERA_LAYOUT, umov_bitsContract.BitsEntry.CameraOriantion.LANDSCAPE.getValue());
        bitValues.put(umov_bitsContract.BitsEntry.COLUMN_CAMERA_DIRECTION, umov_bitsContract.BitsEntry.CameraDirection.FRONT.getValue());
        bitValues.put(umov_bitsContract.BitsEntry.COLUMN_DURATION, 2000);
        bitValues.put(umov_bitsContract.BitsEntry.COLUMN_TEXT_INSTRUCTION,"Welcome to uMovie." + System.lineSeparator() + "Look at the camera and say your greerting!" +
                System.lineSeparator() + "Hi - Hello - Whats up? - Cowabunga!  - Hip-hip  - Cheers...Ready?");

        return bitValues;
    }



    /*
        Students: You can uncomment this function once you have finished creating the
        LocationEntry part of the WeatherContract as well as the WeatherDbHelper.
     */
    static long insertSampleUmovBitsValues(Context context) {
        // insert our test records into the database
        umov_bitsDbHelper dbHelper = new umov_bitsDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createUMovBitValues();

        long locationRowId;
        locationRowId = db.insert(umov_bitsContract.BitsEntry.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert North Pole Location Values", locationRowId != -1);

        return locationRowId;
    }

    /*
        Students: The functions we provide inside of TestProvider use this utility class to test
        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
        CTS tests.

        Note that this only tests that the onChange function is called; it does not test that the
        correct Uri is returned.
     */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }
/***
        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
 ****/
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
