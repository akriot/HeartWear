package com.clemed.heartwear.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MonitorService extends Service {
    public MonitorService() {
    }

    private static final long waitTime = 15000;

    private static boolean mustStop = false;
    private static final String validicUrl =  "https://heartware-api.herokuapp.com/status";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Monitor monitor = new Monitor();
        monitor.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        mustStop = true;
        super.onDestroy();
    }

    private class Monitor extends Thread{

        public Monitor(){}

        public void run(){

            while(!mustStop) {

                try {
                    URL obj = new URL(validicUrl);

                    try {

                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        // optional default is GET
                        con.setRequestMethod("GET");


                        int responseCode = con.getResponseCode();
                        System.out.println("\nSending 'GET' request to URL : " + obj);
                        System.out.println("Response Code : " + responseCode);

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();


                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);

                        }

                        try {
                            JSONObject object = new JSONObject(response.toString());
                            String color = object.get("status").toString();
                            Intent intent = new Intent(getApplicationContext(), WidgetUpdateService.class);
                            intent.putExtra(WidgetUpdateService.COLOR_CODE_EXTRA, color);
                            startService(intent);
                        }catch (JSONException ex){

                        }


                        System.out.println(response.toString());


                        in.close();
                    } catch (IOException ex) {

                    }
                } catch (MalformedURLException e) {

                }

                Object temp = new Object();
                try{
                    synchronized (temp) {
                        temp.wait(waitTime);
                    }
                }catch(InterruptedException e){

                }
            }
        }

    }



}
