package com.blues.money_saver;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.Contacts;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blues.money_saver.data.MoneyContract;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

import static android.R.attr.data;

/**
 * Created by Blues on 25/09/2016.
 */

public class OverviewRecycleAdapter extends RecyclerView.Adapter<OverviewRecycleAdapter.OverviewRecycleViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int recycleitems = 1;
    private Cursor mCursor;
    private String month;

    public enum ITEM_TYPE {
        ITEM_TYPE_TEXT,
        ITEM_TYPE_PIECHART,
    }

    public OverviewRecycleAdapter(Context context)
    {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public OverviewRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
       if (viewType == ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()) {
           return new OverviewRecycleViewHolder(mLayoutInflater.inflate(R.layout.list_item_summary, parent, false));
      }else if(viewType == ITEM_TYPE.ITEM_TYPE_PIECHART.ordinal()) {
           return new OverviewRecycleViewHolder(mLayoutInflater.inflate(R.layout.overview_pie, parent, false));
       }
     else
          return null;

    }

    @Override
    public void onBindViewHolder(OverviewRecycleViewHolder holder, int position)
    {
        float income, payout, balance;

       if(getItemViewType(position)==ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()) {
            mCursor.moveToPosition(position);


            income = mCursor.getFloat(mCursor.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_INCOME));
            payout = mCursor.getFloat(mCursor.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_PAYOUT));
            balance = mCursor.getFloat(mCursor.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_BALANCE));
            month = mCursor.getString(mCursor.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_MONTH));
            holder.mMonthTextview.setText(month);
            holder.mIncomeTextview.setText(income + "");
            holder.mPayoutTextview.setText(payout + "");
            holder.mBalanceTextview.setText(balance + "");
        }
       else if(getItemViewType(position)==ITEM_TYPE.ITEM_TYPE_PIECHART.ordinal())
       {
           boolean hasLabels = true;
           boolean hasLabelForSelected = false;
            boolean hasLabelsOutside = false;
           boolean hasCenterCircle = false;
           float daily, utility,insurance;
           mCursor.moveToFirst();

           daily = mCursor.getFloat(mCursor.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_DAILY));
           utility = mCursor.getFloat(mCursor.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_UTILITY));
           insurance = mCursor.getFloat(mCursor.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_INSURANCE));


           holder.chart.setVisibility(View.INVISIBLE);

           if(daily!=0 || utility !=0 || insurance != 0) {
               holder.pieEmptyTextview.setVisibility(View.INVISIBLE);
               holder.chart.setVisibility(View.VISIBLE);
               List<SliceValue> values = new ArrayList<SliceValue>();
               //for (int i = 0; i < numValues; ++i) {
               SliceValue sliceValue = new SliceValue(daily, mContext.getColor(R.color.pie_daily));
               values.add(sliceValue);
               sliceValue = new SliceValue(utility, mContext.getColor(R.color.pie_utility));
               values.add(sliceValue);
               sliceValue = new SliceValue(insurance, mContext.getColor(R.color.pie_insurance));
               values.add(sliceValue);
               // }

               holder.data = new PieChartData(values);
               holder.data.setHasLabels(hasLabels);
               holder.data.setHasLabelsOnlyForSelected(hasLabelForSelected);
               holder.data.setHasLabelsOutside(hasLabelsOutside);
               holder.data.setHasCenterCircle(hasCenterCircle);

               holder.chart.setCircleFillRatio(0.7f);
               holder.chart.setValueSelectionEnabled(hasLabelForSelected);
               holder.chart.setPieChartData(holder.data);
               holder.chart.setOnValueTouchListener(new ValueTouchListener());
           }
       }

    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {


        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            String menuid = "";
            switch (arcIndex)
            {
                case 0: menuid = mContext.getString(R.string.nav_daily_str);
                    break;
                case 1: menuid = mContext.getString(R.string.nav_utility_str);
                    break;
                case 2: menuid = mContext.getString(R.string.nav_insurance_str);
                    break;
            }
            TabFragment tabfragment = new TabFragment();
            Utility.setCategory(menuid);
            ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main_fragment, tabfragment)
                    .commit();
            Toast.makeText(mContext, Utility.payoutName[arcIndex] +" "+ value.getValue(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    @Override
    public int getItemViewType(int position){
        return position % 2 == 0 ? ITEM_TYPE.ITEM_TYPE_TEXT.ordinal() : ITEM_TYPE.ITEM_TYPE_PIECHART.ordinal();
    }
    @Override
    public int getItemCount()
    {
        if(mCursor ==null) return 0;
        return 2;//mCursor.getCount();
    }

    public static class OverviewRecycleViewHolder extends RecyclerView.ViewHolder {

        TextView mIncomeTextview;
        TextView mPayoutTextview;
        TextView mBalanceTextview;
        TextView mMonthTextview;
        TextView pieEmptyTextview;
        PieChartView chart;
        PieChartData data;


        OverviewRecycleViewHolder(View view)
        {
            super(view);
            mIncomeTextview = (TextView) view.findViewById(R.id.item_income);
            mPayoutTextview = (TextView) view.findViewById(R.id.item_payout);
            mBalanceTextview = (TextView) view.findViewById(R.id.item_balance);
            mMonthTextview = (TextView) view.findViewById(R.id.item_date);
            pieEmptyTextview = (TextView) view.findViewById(R.id.pie_empty);
            chart = (PieChartView) view.findViewById(R.id.chart);
        }
    }

    public void swapCursor(Cursor newCursor)
    {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
