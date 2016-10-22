package com.blues.money_saver.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Blues on 04/09/2016.
 */
public class MoneyContract {
    public static final String CONTENT_AUTHORITY = "com.blues.money_saver";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_MONEY = "money";
    public static final String PATH_SUMMARY = "summary";



    public static final class MoneyEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MONEY).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MONEY;

        public static final String MONEY_TABLE = "money";
        public static final String COLUMN_MONEY_ID = "money_id";
        public static final String COLUMN_MONEY_AMOUNT = "money_amount";
        public static final String COLUMN_MONEY_CATEGORY = "money_category";
        public static final String COLUMN_MONEY_DATE_Year = "money_date_Year";
        public static final String COLUMN_MONEY_DATE_Month = "money_date_Month";
        public static final String COLUMN_MONEY_DATE_Date = "money_date_Date";
        public static final String COLUMN_MONEY_DETAILS = "money_details";
        public static final String COLUMN_MONEY_CHANGE_ABLE = "y";



        public static Uri buildMoneyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }




    }

    public static final class SummaryEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUMMARY).build();
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SUMMARY;
        public static final String SUMMARY_TABLE = "summary";
        public static final String COLUMN_SUMMARY_INCOME = "summary_income";
        public static final String COLUMN_SUMMARY_PAYOUT = "summary_payout";
        public static final String COLUMN_SUMMARY_BALANCE = "summary_balance";
        public static final String COLUMN_SUMMARY_MONTH = "summary_Month";
        public static final String COLUMN_SUMMARY_DAILY = "summary_daily";
        public static final String COLUMN_SUMMARY_UTILITY = "summary_utility";
        public static final String COLUMN_SUMMARY_INSURANCE = "summary_insurance";

        public static Uri buildMoneyUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }


    }
}
