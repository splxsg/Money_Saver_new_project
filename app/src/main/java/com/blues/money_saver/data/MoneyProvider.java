package com.blues.money_saver.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Blues on 04/09/2016.
 */
public class MoneyProvider extends ContentProvider{
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoneyDbHelper mOpenHelper;
    private static final SQLiteQueryBuilder updateSummaryquery = new SQLiteQueryBuilder();




    static final int MONEY = 100;
    static final int SUMMARY = 200;
    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoneyContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MoneyContract.PATH_MONEY, MONEY);
        matcher.addURI(authority, MoneyContract.PATH_SUMMARY, SUMMARY);
        return matcher;
    }

    public static void updateSummary()
    {
        Cursor mCursor;


    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new MoneyDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // only one table here
            case MONEY:
                return MoneyContract.MoneyEntry.CONTENT_ITEM_TYPE;
            case SUMMARY:
                return MoneyContract.SummaryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }




    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case MONEY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MoneyContract.MoneyEntry.MONEY_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case SUMMARY:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MoneyContract.SummaryEntry.SUMMARY_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Log.v("IIIIII",match+"");
        Uri returnUri;

        switch (match) {
            case MONEY: {
                long _id = db.insert(MoneyContract.MoneyEntry.MONEY_TABLE, null, values);
                if (_id > 0)
                    returnUri = MoneyContract.MoneyEntry.buildMoneyUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case SUMMARY: {
                long _id = db.insert(MoneyContract.SummaryEntry.SUMMARY_TABLE, null, values);
                if (_id > 0)
                    returnUri = MoneyContract.SummaryEntry.buildMoneyUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case MONEY:
                rowsDeleted = db.delete(
                        MoneyContract.MoneyEntry.MONEY_TABLE, selection, selectionArgs);
                break;
            case SUMMARY:
                rowsDeleted = db.delete(
                        MoneyContract.SummaryEntry.SUMMARY_TABLE, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MONEY:
                rowsUpdated = db.update(MoneyContract.MoneyEntry.MONEY_TABLE, values, selection,
                        selectionArgs);
                break;
            case SUMMARY:
                rowsUpdated = db.update(MoneyContract.SummaryEntry.SUMMARY_TABLE, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case 100:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MoneyContract.MoneyEntry.MONEY_TABLE, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case SUMMARY:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(MoneyContract.SummaryEntry.SUMMARY_TABLE, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


}
