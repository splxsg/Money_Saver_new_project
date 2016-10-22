package com.blues.money_saver;

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
import android.widget.TextView;


import com.blues.money_saver.data.MoneyContract;

/**
 * Created by Blues on 06/09/2016.
 */
public class CategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = CategoryFragment.class.getSimpleName();
    private MoneyRecycleAdapter mMoneyAdapter;
    private static final int CURSOR_LOADER_ID = 0;
    private static final int Money_LOADER = 0;

    String monthindex,categoryindex;


    public CategoryFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       monthindex = getArguments().getString("monthFragment");
        categoryindex = Utility.getCategory();
        View rootView = inflater.inflate(R.layout.fragment_category,container,false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        ((TextView) rootView.findViewById(R.id.recyclerview_money_empty)).setText("no information in this month");

        mMoneyAdapter = new MoneyRecycleAdapter(getActivity(),rootView.findViewById(R.id.recyclerview_money_empty));
        recyclerView.setAdapter(mMoneyAdapter);
        /*mMoneyAdapter = new MoneyRecycleAdapter(getActivity(), new MoneyAdapter.MoneyAdapterOnClickHandler() {
           @Override
            public void onClick(Long date, MoneyAdapter.MoneyAdapterViewHolder vh) {

            }
        });*/
       // recyclerView.setAdapter(new MoneyRecycleAdapter(getActivity()));
        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(Money_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri moneyUri = MoneyContract.MoneyEntry.CONTENT_URI;
        String selectmonth = MoneyContract.MoneyEntry.COLUMN_MONEY_DATE_Month;
        String selectcategory = MoneyContract.MoneyEntry.COLUMN_MONEY_CATEGORY;

        return new CursorLoader(getActivity(),
                moneyUri,
                null,
                selectmonth + "=? AND " + selectcategory + "=?",
                new String[] {monthindex,categoryindex},
                null);

    }
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
       // public void onItemSelected(Uri dateUri, MoneyAdapter.MoneyAdapterViewHolder vh);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
     mMoneyAdapter.swapCursor(data);
      //  Utility.updateSummary(getActivity(),monthindex);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mMoneyAdapter.swapCursor(null);
    }




}
