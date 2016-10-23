package com.blues.money_saver.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.blues.money_saver.MainActivity;
import com.blues.money_saver.R;

/**
 * Created by Blues on 23/10/2016.
 */

public class NewMoneyWidget extends AppWidgetProvider  {


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_widget);
            //views.setTextViewText(R.id.widget_stock,"test");

            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            //PendingIntent pendingUpdateIntent = PendingIntent.getActivity(context,0,updateintent,0);
            //views.setOnClickPendingIntent(R.id.widget_stock_framelayout, pendingIntent);
            views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            //views.setOnClickPendingIntent(R.id.widget_update,pendingUpdateIntent);

            // Set up the collection

                setRemoteAdapter(context, views);

            /*boolean useDetailActivity = context.getResources()
                    .getBoolean(R.bool.use_detail_activity);
            Intent clickIntentTemplate = useDetailActivity
                    ? new Intent(context, DetailStockActivity.class)
                    : new Intent(context, MyStocksActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_stock, clickPendingIntentTemplate);*/
            views.setEmptyView(R.id.widget_card, R.id.widget_empty);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        context.startService(new Intent(context, MoneyWidgetViewService.class));
    }


    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_card);
        }
    }
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_card,
                new Intent(context,MoneyWidgetViewService.class));
    }

}
