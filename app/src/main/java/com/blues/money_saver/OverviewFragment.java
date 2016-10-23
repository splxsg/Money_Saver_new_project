package com.blues.money_saver;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blues.money_saver.data.MoneyContract;

import java.util.Vector;

import static com.blues.money_saver.CategoryFragment.LOG_TAG;

/**
 * Created by Blues on 25/09/2016.
 */

public class OverviewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    String monthindex;
    private Intent updateintent;
    private OverviewRecycleAdapter mOverviewAdapter;
    private static final int CURSOR_LOADER_ID = 0;
    private static final int Summary_LOADER = 0;

    public OverviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        updateintent = new Intent(getActivity(),OverviewUpdateIntent.class);
        monthindex = getArguments().getString("monthFragment");
        updateintent.putExtra("monthFragment",monthindex+"");

        initialsummary();

        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_summary);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        mOverviewAdapter = new OverviewRecycleAdapter(getActivity());
        recyclerView.setAdapter(mOverviewAdapter);

        if(getResources().getString(R.string.flavors) == getResources().getString(R.string.freeflavors))
        {
            AdviewActivity adviewActivity = new AdviewActivity(rootView,getActivity());
            adviewActivity.showad();
        }


        return rootView;
    }


    private void initialsummary()
    {
        Uri inserteduri;
        Cursor summaryCursor;
        String select_month = MoneyContract.SummaryEntry.COLUMN_SUMMARY_MONTH;
        for(int i=0;i<12;i++)
        {
            summaryCursor = getContext().getContentResolver().query(MoneyContract.SummaryEntry.CONTENT_URI,
                    null,
                    select_month + "=?",
                    new String[]{Utility.monthConvert(i)},
                    null);
            if(!summaryCursor.moveToFirst())
            {
                Vector<ContentValues> cVVector = new Vector<ContentValues>(1);
                ContentValues moneyValues = new ContentValues();

                moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_INCOME,0);
                moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_PAYOUT,0);
                moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_BALANCE,0);
                moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_MONTH,Utility.monthConvert(i));
                moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_DAILY,0);
                moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_UTILITY,0);
                moneyValues.put(MoneyContract.SummaryEntry.COLUMN_SUMMARY_INSURANCE,0);

                cVVector.add(moneyValues);
                int inserted = 0;
                inserteduri = getContext().getContentResolver().insert(MoneyContract.SummaryEntry.CONTENT_URI, moneyValues);
                if(ContentUris.parseId(inserteduri) != -1)
                    Log.d(LOG_TAG, "FetchWeatherTask Complete. " + inserted + " Inserted");
            }
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(Summary_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri moneyUri = MoneyContract.SummaryEntry.CONTENT_URI;
        String select_month = MoneyContract.SummaryEntry.COLUMN_SUMMARY_MONTH;
        getActivity().startService(updateintent);
        return new CursorLoader(getActivity(),
                moneyUri,
                null,
                select_month + "=?",
                new String[]{monthindex},
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        mOverviewAdapter.swapCursor(data);

    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mOverviewAdapter.swapCursor(null);
    }

}
