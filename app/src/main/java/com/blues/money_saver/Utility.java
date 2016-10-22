package com.blues.money_saver;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.blues.money_saver.data.MoneyContract;
import com.blues.money_saver.data.MoneyContract.SummaryEntry;

import java.util.Vector;

import static com.blues.money_saver.CategoryFragment.LOG_TAG;

/**
 * Created by Blues on 21/09/2016.
 */
public class Utility {
    public static String[] Monthontab={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    public static String[] payoutName={"Daily","utility","insurance"};
    private static String category;
    private static int tabind;

    public static String monthConvert(int month)
    {
        if(month >=0 && month <=11)
       return Monthontab[month];
        else
            return "none";
    }

    public static void setCategory(String cat){category=cat;}

    public static String getCategory(){return category;}

    public static void setTabindex(int tabindex){tabind=tabindex;}

    public static int getTabindex(){return tabind;}


    public static void updateSummary(Context context,String month)
    {
        Cursor mMoneyCursor;
        float income,payout,balance,daily,utility,insurance;
        String select_month, select_summary_month,income_str,loan_str,daily_str,utility_str,insurance_str;
        Uri summaryUri,moneyUri;
        int columnCategory, columnAmount;
        String strcolCategory,strcolAmount;

        summaryUri = MoneyContract.SummaryEntry.CONTENT_URI;
        moneyUri  = MoneyContract.MoneyEntry.CONTENT_URI;

        select_month = MoneyContract.MoneyEntry.COLUMN_MONEY_DATE_Month;
        income_str = context.getString(R.string.nav_income_str);
        loan_str = context.getString(R.string.nav_loan_str);
        daily_str = context.getString(R.string.nav_daily_str);
        utility_str = context.getString(R.string.nav_utility_str);
        insurance_str = context.getString(R.string.nav_insurance_str);


        income = 0f;
        payout = 0f;
        daily = 0f;
        utility = 0f;
        insurance = 0f;
        mMoneyCursor = context.getContentResolver().query(
                moneyUri,
                null,
                select_month + "=?",
                new String[] {month},
                null);


        columnCategory = mMoneyCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_CATEGORY);
        columnAmount = mMoneyCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_AMOUNT);
        if(mMoneyCursor.moveToFirst())
        {
                while(!mMoneyCursor.isAfterLast())
                {
                    strcolCategory = mMoneyCursor.getString(columnCategory);
                    strcolAmount = mMoneyCursor.getString(columnAmount);


                    if(strcolCategory.equals(income_str)||
                            strcolCategory.equals(loan_str))
                    {
                        income += Float.parseFloat(strcolAmount);
                    }
                    else
                    {
                        if(strcolCategory.equals(daily_str))
                            daily += Float.parseFloat(strcolAmount);
                        else if(strcolCategory.equals(utility_str))
                            utility += Float.parseFloat(strcolAmount);
                        else if(strcolCategory.equals(insurance_str))
                            insurance += Float.parseFloat(strcolAmount);
                        payout += Float.parseFloat(strcolAmount);
                    }
                    mMoneyCursor.moveToNext();
                }
        }

        balance = income - payout;

        if(mMoneyCursor.moveToFirst())
        {
            Vector<ContentValues> cVVector = new Vector<ContentValues>(1);
            ContentValues moneyValues = new ContentValues();
            select_summary_month = SummaryEntry.COLUMN_SUMMARY_MONTH;

            moneyValues.put(SummaryEntry.COLUMN_SUMMARY_INCOME,income);
            moneyValues.put(SummaryEntry.COLUMN_SUMMARY_PAYOUT,payout);
            moneyValues.put(SummaryEntry.COLUMN_SUMMARY_BALANCE,balance);
            moneyValues.put(SummaryEntry.COLUMN_SUMMARY_DAILY,daily);
            moneyValues.put(SummaryEntry.COLUMN_SUMMARY_UTILITY,utility);
            moneyValues.put(SummaryEntry.COLUMN_SUMMARY_INSURANCE,insurance);

            cVVector.add(moneyValues);
            if(context.getContentResolver().update(summaryUri,
                    moneyValues,
                    select_summary_month + "=?",
                    new String[] {month}) != -1)
                Log.d(LOG_TAG, "Overview has been updated");
        }

    }

}
