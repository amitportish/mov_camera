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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mov.android.camera2video.data.umov_bitsContract.BitsEntry;

/**
 * Manages a local database for weather data.
 */
public class umov_bitsDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "umov.db";

    public umov_bitsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_UMOV_BITS_TABLE = "CREATE TABLE " + BitsEntry.TABLE_NAME + " (" +
                BitsEntry._ID + " INTEGER PRIMARY KEY," +
                BitsEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BitsEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                BitsEntry.COLUMN_TYPE + " TINYINT NOT NULL, " +
                BitsEntry.COLUMN_CAMERA_LAYOUT + " TINYINT NOT NULL " +
                BitsEntry.COLUMN_CAMERA_DIRECTION + " TINYINT NOT NULL " +
                BitsEntry.COLUMN_DURATION + " INTEGER NOT NULL " +
                BitsEntry.COLUMN_ICON_URI + " TEXT, " +
                BitsEntry.COLUMN_TEXT_INSTRUCTION + " TEXT, " +
                BitsEntry.COLUMN_VIDEO_INSTRUCTION_URI+ " TEXT, " +
                BitsEntry.COLUMN_KARAOKE_AUDIO_URI + " TEXT, " +
                BitsEntry.COLUMN_KARAOKE_LYRICS + " TEXT, " +
                BitsEntry.COLUMN_AUDIO_INSTRUCTION_URI + " TEXT, " +
                " );";


        sqLiteDatabase.execSQL(SQL_CREATE_UMOV_BITS_TABLE );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BitsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
