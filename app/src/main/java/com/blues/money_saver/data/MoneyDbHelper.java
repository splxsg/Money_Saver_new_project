package com.blues.money_saver.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blues.money_saver.data.MoneyContract.MoneyEntry;
import com.blues.money_saver.data.MoneyContract.SummaryEntry;
/**
 * Created by Blues on 04/09/2016.
 */
public class MoneyDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 17;
    static final String DATABASE_NAME = "money.db";
    public MoneyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createMoneyTable(SQLiteDatabase sqLiteDatabase)
    {
        final String SQL_CREATE_MONEY_TABLE = "CREATE TABLE " + MoneyEntry.MONEY_TABLE + " (" +
                MoneyEntry._ID + " INTEGER PRIMARY KEY," +
                MoneyEntry.COLUMN_MONEY_ID + " TEXT NOT NULL," +
                MoneyEntry.COLUMN_MONEY_DATE_Year + " TEXT NOT NULL," +
                MoneyEntry.COLUMN_MONEY_DATE_Month + " TEXT NOT NULL," +
                MoneyEntry.COLUMN_MONEY_DATE_Date + " TEXT NOT NULL," +
                MoneyEntry.COLUMN_MONEY_AMOUNT + " TEXT NOT NULL," +
                MoneyEntry.COLUMN_MONEY_CATEGORY + " TEXT NOT NULL," +
                MoneyEntry.COLUMN_MONEY_DETAILS + " TEXT NOT NULL," +
                MoneyEntry.COLUMN_MONEY_CHANGE_ABLE + " TEXT NOT NULL" +
                " );";
        Log.v("MONEY CREATE",SQL_CREATE_MONEY_TABLE.toString());
        sqLiteDatabase.execSQL(SQL_CREATE_MONEY_TABLE);
        Log.v("MoneyTable","create! ");
    }

    private void createSummaryTable(SQLiteDatabase sqLiteDatabase)
    {
        final String SQL_CREATE_SUMMARY_TABLE = "CREATE TABLE " + SummaryEntry.SUMMARY_TABLE + " (" +
                SummaryEntry._ID + " INTEGER PRIMARY KEY," +
                SummaryEntry.COLUMN_SUMMARY_MONTH + " TEXT NOT NULL," +
                SummaryEntry.COLUMN_SUMMARY_INCOME + " FLOAT," +
                SummaryEntry.COLUMN_SUMMARY_PAYOUT + " FLOAT," +
                SummaryEntry.COLUMN_SUMMARY_BALANCE + " FLOAT," +
                SummaryEntry.COLUMN_SUMMARY_DAILY + " FLOAT," +
                SummaryEntry.COLUMN_SUMMARY_UTILITY + " FLOAT," +
                SummaryEntry.COLUMN_SUMMARY_INSURANCE + " FLOAT" +
        " );";
        Log.v("SUMMARY CREATE",SQL_CREATE_SUMMARY_TABLE.toString());
        sqLiteDatabase.execSQL(SQL_CREATE_SUMMARY_TABLE);
        Log.v("SummaryTable","create! ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        createMoneyTable(sqLiteDatabase);
        createSummaryTable(sqLiteDatabase);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoneyEntry.MONEY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SummaryEntry.SUMMARY_TABLE);
        onCreate(sqLiteDatabase);
    }

    
}
