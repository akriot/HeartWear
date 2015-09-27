package com.clemed.heartwear;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.clemed.heartwear.server.ServerUtilities;

public class DialogActivity extends Activity {

    public static boolean isWindowOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title);
        builder.setMessage(R.string.message);

        builder.setPositiveButton(R.string.noEmergency, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Runnable runner = new Runnable() {
                    @Override
                    public void run() {
                        //no means falst positive, everything is ok
                        ServerUtilities.sendFeedback(getResources().getString(R.string.no));
                    }
                };
                runner.run();
                dialog.dismiss();
                isWindowOpen = false;
                finish();
            }
        });
        builder.setNegativeButton(R.string.emergency, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // clicking "no, this is an emergency" is not a false positive
                ServerUtilities.sendFeedback(getResources().getString(R.string.yes));
                sendGcmAlert();
                dialog.dismiss();
                isWindowOpen = false;
                finish();
            }
        });
        if(!isWindowOpen) {
            builder.create().show();
            isWindowOpen = true;
        }

    }


    public void sendGcmAlert(){

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                ServerUtilities.sendAlert(getApplicationContext());
            }
        };
        thread.start();
    }


}
