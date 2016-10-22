package com.blues.money_saver;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blues.money_saver.data.MoneyContract;

/**
 * Created by Blues on 20/09/2016.
 */
public class MoneyRecycleAdapter extends RecyclerView.Adapter<MoneyRecycleAdapter.MoneyRecycleViewHolder>{

        private final LayoutInflater mLayoutInflater;
    private Cursor mCursor;
        private final Context mContext;
    private View mEmptyview;
        private String[] mTitles;
   // private String selectmonth = MoneyContract.MoneyEntry.COLUMN_MONEY_DATE;

        public MoneyRecycleAdapter(Context context, View emptyview) {
            this.mContext = context;
            this.mEmptyview = emptyview;
            mLayoutInflater = LayoutInflater.from(context);


        }

        @Override
        public MoneyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MoneyRecycleViewHolder(mLayoutInflater.inflate(R.layout.list_item_category, parent, false));
        }

        @Override
        public void onBindViewHolder(MoneyRecycleViewHolder holder, int position) {
            mCursor.moveToPosition(position);
           String datelongformat = mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_DATE_Year))+"-"+
                   mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_DATE_Month))+"-"+
                   mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_DATE_Date));
            holder.mDateTextview.setText(datelongformat);
            holder.mAmountTextview.setText(mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_AMOUNT)));
            holder.mCategoryTextview.setText(mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_CATEGORY)));
            holder.mDetailsTextview.setText(mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_DETAILS)));
            holder.mDateTextview.setContentDescription(mContext.getString(R.string.a11y_DateTextview,
                    datelongformat));
            holder.mCategoryTextview.setContentDescription(mContext.getString(R.string.a11y_CategoryTextview,
                    mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_CATEGORY))));
            holder.mAmountTextview.setContentDescription(mContext.getString(R.string.a11y_AmountTextview,
                    mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_AMOUNT))));
            holder.mDetailsTextview.setContentDescription(mContext.getString(R.string.a11y_DetailsTextview,
                    mCursor.getString(mCursor.getColumnIndex(MoneyContract.MoneyEntry.COLUMN_MONEY_DETAILS))));
        }


        @Override
        public int getItemCount() {
            if(mCursor == null) return 0;
            return mCursor.getCount();
           // return mTitles == null ? 0 : mTitles.length;
        }

        public static class MoneyRecycleViewHolder extends RecyclerView.ViewHolder {
          //  @InjectView(R.id.text_view)
           // TextView mTextView;
            TextView mDateTextview;
            TextView mDetailsTextview;
            TextView mAmountTextview;
            TextView mCategoryTextview;

            MoneyRecycleViewHolder(View view) {
                super(view);
                mDateTextview = (TextView) view.findViewById(R.id.list_item_date);
                mDetailsTextview = (TextView) view.findViewById(R.id.list_item_details);
                mAmountTextview = (TextView) view.findViewById(R.id.list_item_amount);
                mCategoryTextview = (TextView) view.findViewById(R.id.list_item_category);



                //ButterKnife.inject(this, view);
               /* view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                    }
                });*/
            }
        }

    public void swapCursor(Cursor newCursor)
    {
        mCursor = newCursor;
        notifyDataSetChanged();
        mEmptyview.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);


    }

}
