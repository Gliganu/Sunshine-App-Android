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
package com.gliga.sunshine.test.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.gliga.sunshine.data.WeatherDbHelper;

import static com.gliga.sunshine.data.WeatherContract.LocationEntry;
import static com.gliga.sunshine.data.WeatherContract.WeatherEntry;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    public static String TEST_CITY_NAME = "North Pole";


    public void testCreateDb() throws Throwable {
        mContext.deleteDatabase(WeatherDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new WeatherDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadLocationTable() {

        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = getLocationContentValues();

        long locationRowId = db.insert(LocationEntry.TABLE_NAME, null, values);

        assertTrue(locationRowId != -1);

        Cursor cursor = db.query(
                LocationEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );


        assertTrue("Error: No records returned from location query ", cursor.moveToFirst());

        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed", cursor, values);

        assertFalse("Error: More than one record returned from the lcoation query ", cursor.moveToNext());

        cursor.close();
        dbHelper.close();
    }

    public void testInsertReadWeatherTable(){

        WeatherDbHelper dbHelper = new WeatherDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues locationContentValues = getLocationContentValues();

        long locationRowId = db.insert(LocationEntry.TABLE_NAME, null, locationContentValues);

        assertTrue("Location entry error",locationRowId != -1);

        ContentValues weatherContentValues = getWeatherContentValues(locationRowId);

        long weatherRowId = db.insert(WeatherEntry.TABLE_NAME,null,weatherContentValues);

        assertTrue("Weather entry error",weatherRowId != -1);

        Cursor weatherCursor = db.query(
                WeatherEntry.TABLE_NAME,  // Table to Query
                null,
                null, // Columns for the "where" clause
                null, // Values for the "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null // sort order
        );

        assertTrue("Error: No records returned from weather query ", weatherCursor.moveToFirst());

        TestUtilities.validateCurrentRecord("Error: Location Query Validation Failed", weatherCursor, weatherContentValues);

        assertFalse("Error: More than one record returned from the weather query ", weatherCursor.moveToNext());


//
//        // If possible, move to the first row of the query results.
//        if (cursor.moveToFirst()) {
//            // Get the value in each column by finding the appropriate column index.
//            int locationIndex = cursor.getColumnIndex(LocationEntry.COLUMN_LOCATION_SETTING);
//            String location = cursor.getString(locationIndex);
//
//            int nameIndex = cursor.getColumnIndex((LocationEntry.COLUMN_CITY_NAME));
//            String name = cursor.getString(nameIndex);
//
//            int latIndex = cursor.getColumnIndex((LocationEntry.COLUMN_COORD_LAT));
//            double latitude = cursor.getDouble(latIndex);
//
//            int longIndex = cursor.getColumnIndex((LocationEntry.COLUMN_COORD_LONG));
//            double longitude = cursor.getDouble(longIndex);
//
//            // Hooray, data was returned!  Assert that it's the right data, and that the database
//            // creation code is working as intended.
//            // Then take a break.  We both know that wasn't easy.
//            assertEquals(testCityName, name);
//            assertEquals(testLocationSetting, location);
//            assertEquals(testLatitude, latitude);
//            assertEquals(testLongitude, longitude);
//
//            // Fantastic.  Now that we have a location, add some weather!
//        } else {
//            // That's weird, it works on MY machine...
//            fail("No values returned :(");
//        }

        ////!!!!!!!!!!!!!!!!!/////////////////////

        // Fantastic.  Now that we have a location, add some weather!
//
//        ContentValues weatherValues = new ContentValues();
//
//        String testDateText = "20141205";
//        double testDegrees = 1.1;
//        double testHumidity = 1.2;
//        double testPressure = 1.3;
//        double testMaxTemp = 75.0;
//        double testMinTemp = 65.0;
//        String testShortDesc = "Asteroids";
//        double testWindSpeed = 5.5;
//        int testWeatherId = 321;
//
//        weatherValues.put(WeatherEntry.COLUMN_LOC_KEY, locationRowId);
//        weatherValues.put(WeatherEntry.COLUMN_DATETEXT, testDateText);
//        weatherValues.put(WeatherEntry.COLUMN_DEGREES, testDegrees);
//        weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, testHumidity);
//        weatherValues.put(WeatherEntry.COLUMN_PRESSURE, testPressure);
//        weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, testMaxTemp);
//        weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, testMinTemp);
//        weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, testShortDesc);
//        weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, testWindSpeed);
//        weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, testWeatherId);
//
//
//        long weatherRowId;
//        weatherRowId = db.insert(WeatherEntry.TABLE_NAME, null, weatherValues);
//
//        // Verify we got a row back.
//        assertTrue(weatherRowId != -1);
//        Log.d(LOG_TAG, "New row id: " + weatherRowId);
//
//        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
//        // the round trip.
//
//        // Specify which columns you want.
//        String[] weatherColumns = {
//                WeatherEntry._ID,
//                WeatherEntry.COLUMN_LOC_KEY,
//                WeatherEntry.COLUMN_DATETEXT,
//                WeatherEntry.COLUMN_DEGREES,
//                WeatherEntry.COLUMN_HUMIDITY,
//                WeatherEntry.COLUMN_PRESSURE,
//                WeatherEntry.COLUMN_MAX_TEMP,
//                WeatherEntry.COLUMN_MIN_TEMP,
//                WeatherEntry.COLUMN_SHORT_DESC,
//                WeatherEntry.COLUMN_WIND_SPEED,
//                WeatherEntry.COLUMN_WEATHER_ID,
//        };
//
//        // A cursor is your primary interface to the query results.
//        Cursor weatherCursor = db.query(
//                WeatherEntry.TABLE_NAME,  // Table to Query
//                weatherColumns,
//                null, // Columns for the "where" clause
//                null, // Values for the "where" clause
//                null, // columns to group by
//                null, // columns to filter by row groups
//                null // sort order
//        );
//
//        // If possible, move to the first row of the query results.
//        if (weatherCursor.moveToFirst()) {
//            // Get the value in each column by finding the appropriate column index.
//            int locationKeyIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_LOC_KEY);
//            int locationKey = weatherCursor.getInt(locationKeyIndex);
//
//            int dateTextIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_DATETEXT);
//            String dateText = weatherCursor.getString(dateTextIndex);
//
//            int degreesIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_DEGREES);
//            double degrees = weatherCursor.getDouble(degreesIndex);
//
//            int humidityIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_HUMIDITY);
//            double humidity = weatherCursor.getDouble(humidityIndex);
//
//            int pressureIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_PRESSURE);
//            double pressure = weatherCursor.getDouble(pressureIndex);
//
//            int maxTempIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_MAX_TEMP);
//            double maxTemp = weatherCursor.getDouble(maxTempIndex);
//
//            int minTempIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_MIN_TEMP);
//            double minTemp = weatherCursor.getDouble(minTempIndex);
//
//            int shortDescIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_SHORT_DESC);
//            String shortDesc = weatherCursor.getString(shortDescIndex);
//
//            int windSpeedIndex = weatherCursor.getColumnIndex(WeatherEntry.COLUMN_WIND_SPEED);
//            double windSpeed = weatherCursor.getDouble(windSpeedIndex);
//
//            assertEquals(locationKey, locationRowId);
//            assertEquals(dateText, testDateText);
//            assertEquals(degrees, testDegrees);
//            assertEquals(humidity, testHumidity);
//            assertEquals(pressure, testPressure);
//            assertEquals(maxTemp, testMaxTemp);
//            assertEquals(minTemp, testMinTemp);
//            assertEquals(shortDesc, testShortDesc);
//            assertEquals(windSpeed, testWindSpeed);
//
//        } else {
//            fail("No values returned from weather :(");
//        }

        weatherCursor.close();
        dbHelper.close();
    }

    public ContentValues getLocationContentValues() {
        ContentValues values = new ContentValues();

        String testLocationSetting = "99705";
        double testLatitude = 64.7488;
        double testLongitude = -147.353;

        values.put(LocationEntry.COLUMN_LOCATION_SETTING, testLocationSetting);
        values.put(LocationEntry.COLUMN_CITY_NAME, TEST_CITY_NAME);
        values.put(LocationEntry.COLUMN_COORD_LAT, testLatitude);
        values.put(LocationEntry.COLUMN_COORD_LONG, testLongitude);


        return values;
    }

    public  ContentValues getWeatherContentValues(long locationRowId) {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(WeatherEntry.COLUMN_LOC_KEY, locationRowId);
        weatherValues.put(WeatherEntry.COLUMN_DATE, "20141205");
        weatherValues.put(WeatherEntry.COLUMN_DEGREES, 1.1);
        weatherValues.put(WeatherEntry.COLUMN_HUMIDITY, 1.2);
        weatherValues.put(WeatherEntry.COLUMN_PRESSURE, 1.3);
        weatherValues.put(WeatherEntry.COLUMN_MAX_TEMP, 75);
        weatherValues.put(WeatherEntry.COLUMN_MIN_TEMP, 65);
        weatherValues.put(WeatherEntry.COLUMN_SHORT_DESC, "Asteroids");
        weatherValues.put(WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        weatherValues.put(WeatherEntry.COLUMN_WEATHER_ID, 321);

        return weatherValues;
    }
}