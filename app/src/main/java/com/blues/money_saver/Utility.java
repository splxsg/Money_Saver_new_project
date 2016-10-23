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
    public static String[] Monthontab={"January","February","March","April","May","June"
            ,"July","August","September","October","November","December"};
    public static String[] Monthshort={"Jan","Feb","Mar","Apr","May","Jun"
            ,"Jul","Aug","Sep","Oct","Nov","Dec"};

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



}
