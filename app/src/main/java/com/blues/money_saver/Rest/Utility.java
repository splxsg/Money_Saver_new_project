package com.blues.money_saver.Rest;

/**
 * Created by Blues on 21/09/2016.
 */
public class Utility {
    public static String[] Monthontab={"January","February","March","April","May","June"
            ,"July","August","September","October","November","December"};
    public static String[] Monthshort={"Jan","Feb","Mar","Apr","May","Jun"
            ,"Jul","Aug","Sep","Oct","Nov","Dec"};

    public static String[] payoutName={"Daily","Utility","Insurance"};
    private static String category;
    private static int tabind;

    public static String monthConvert(int month)
    {
        if(month >=0 && month <=11)
       return Monthontab[month];
        else
            return null;
    }

    public static void setCategory(String cat){category=cat;}

    public static String getCategory(){return category;}

    public static void setTabindex(int tabindex){tabind=tabindex;}

    public static int getTabindex(){return tabind;}



}
