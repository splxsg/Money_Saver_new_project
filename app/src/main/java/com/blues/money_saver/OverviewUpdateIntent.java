package com.blues.money_saver;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.blues.money_saver.data.MoneyContract;
import com.blues.money_saver.widget.NewMoneyWidget;

import java.util.Vector;

import static com.blues.money_saver.CategoryFragment.LOG_TAG;

/**
 * Created by Blues on 22/10/2016.
 */

public class OverviewUpdateIntent extends IntentService {

    Intent intent;

    public OverviewUpdateIntent() {
        super(OverviewUpdateIntent.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        this.intent = intent;
        Cursor mMoneyCursor;
        float income, payout, balance, daily, utility, insurance;
        String select_month, select_summary_month, income_str, loan_str, daily_str, utility_str, insurance_str;
        Uri summaryUri, moneyUri;
        int columnCategory, columnAmount;
        String strcolCategory, strcolAmount;
        String month;
        month = intent.getStringExtra("monthFragment");

        summaryUri = MoneyContract.SummaryEntry.CONTENT_URI;
        moneyUri = MoneyContract.MoneyEntry.CONTENT_URI;
        select_month = MoneyContract.MoneyEntry.COLUMN_MONEY_DATE_Month;
        income_str = getResources().getString(R.string.nav_income_str);
        loan_str = getResources().getString(R.string.nav_loan_str);
        daily_str = getResources().getString(R.string.nav_daily_str);
        utility_str = getResources().getString(R.string.nav_utility_str);
        insurance_str = getResources().getString(R.string.nav_insurance_str);


        income = 0f;
        payout = 0f;
        daily = 0f;
        utility = 0f;
        insurance = 0f;
        mMoneyCursor = getContentResolver().query(
                moneyUri,
                null,
                select_month + "=?",
                new String[]{month},
                null);


        columnCategory = mMoneyCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_CATEGORY);
        columnAmount = mMoneyCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_AMOUNT);
        if (mMoneyCursor.moveToFirst()) {
            while (!mMoneyCursor.isAfterLast()) {
                strcolCategory = mMoneyCursor.getString(columnCategory);
                strcolAmount = mMoneyCursor.getString(columnAmount);


                if (strcolCategory.equals(income_str) ||
                        strcolCategory.equals(loan_str)) {
                    income += Float.parseFloat(strcolAmount);
                } else {
                    if (strcolCategory.equals(daily_str))
                        daily += Float.parseFloat(strcolAmount);
                    else if (strcolCategory.equals(utility_str))
                        utility += Float.parseFloat(strcolAmount);
                    else if (strcolCategory.equals(insurance_str))
                        insurance += Float.parseFloat(strcolAmount);
                    payout += Float.parseFloat(strcolAmount);
                }
                mMoneyCursor.moveToNext();
            }
        }

        balance = income - payout;

        if (mMoneyCursor.moveToFirst()) {
            Vector<ContentValues> cVVector = new Vector<ContentValues>(1);
            ContentValues moneyValues = new ContentValues();
            select_summary_month = MoneyContract.SummaryEntry.COLUMN_SUMMARY_MONTH;

            moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_INCOME, income);
            moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_PAYOUT, payout);
            moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_BALANCE, balance);
            moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_DAILY, daily);
            moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_UTILITY, utility);
            moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_INSURANCE, insurance);

            cVVector.add(moneyValues);
            if (getContentResolver().update(summaryUri,
                    moneyValues,
                    select_summary_month + "=?",
                    new String[]{month}) != -1)
                Log.d(LOG_TAG, "Overview has been updated");
        }


        Intent updateIntent = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        this.sendBroadcast(updateIntent);

    }
}

