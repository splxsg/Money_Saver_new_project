package com.blues.money_saver.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.blues.money_saver.R;
import com.blues.money_saver.Rest.Utility;
import com.blues.money_saver.data.MoneyContract;

/**
 * Created by Blues on 23/10/2016.
 */

public class MoneyWidgetViewService extends RemoteViewsService {
    public final String LOG_TAG = MoneyWidgetViewService.class.getSimpleName();

    private Context mContext;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mContext = this.getApplicationContext();
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                final long identityToken = Binder.clearCallingIdentity();
                //data.moveToFirst();
                Uri moneyUri = MoneyContract.SummaryEntry.CONTENT_URI;
                String select_month = MoneyContract.SummaryEntry.COLUMN_SUMMARY_MONTH;
                data = mContext.getContentResolver().query(
                        moneyUri,
                        null,
                        select_month + "= ?",
                        new String[]{Utility.monthConvert(9)},
                        null);
                data.moveToFirst();

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {// position>data.getCount()) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_money_list_item);
                views.setTextViewText(R.id.widget_item_income, data.getString(data.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_INCOME)));
                views.setTextViewText(R.id.widget_item_payout, data.getString(data.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_PAYOUT)));
                views.setTextViewText(R.id.widget_item_balance, data.getString(data.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_BALANCE)));
                views.setTextViewText(R.id.widget_item_date,data.getString(data.getColumnIndex(MoneyContract.SummaryEntry.COLUMN_SUMMARY_MONTH)));
                return views;
            }




            /*@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                views.setContentDescription(R.id.widget_icon, description);
            }*/

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_money_list_item);
                // return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
