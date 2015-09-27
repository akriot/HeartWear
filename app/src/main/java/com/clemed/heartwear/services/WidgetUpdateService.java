package com.clemed.heartwear.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;

import com.clemed.heartwear.DialogActivity;
import com.clemed.heartwear.widget.StatusMonitor;


public class WidgetUpdateService extends IntentService {

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }


    private final int Red = Color.RED;
    private final int Green = Color.GREEN;
    private final int Yellow = Color.YELLOW;

    public static final String COLOR_CODE_EXTRA = "color";

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if(intent.hasExtra(COLOR_CODE_EXTRA)){
                String extra = intent.getStringExtra(COLOR_CODE_EXTRA);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                ComponentName thisAppWidget = new ComponentName(getPackageName(), StatusMonitor.class.getName());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
                if(extra.equals("red")){

                    askFalsePositive();
                    StatusMonitor.setBackgroundColor(this, appWidgetManager, appWidgetIds, Red);

                }else if(extra.equals("yellow")){

                    askFalsePositive();
                    StatusMonitor.setBackgroundColor(this, appWidgetManager, appWidgetIds, Yellow);
                }else if(extra.equals("green")){

                    StatusMonitor.setBackgroundColor(this, appWidgetManager, appWidgetIds, Green);
                }
            }
        }
    }


    private void askFalsePositive(){
        //to create a dialog, we must start an activity. Cannot create
        //dialogs from services
        Intent intent = new Intent(this, DialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
