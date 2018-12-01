package com.satyajiit.sms;



import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class ReadSmsAct extends AppCompatActivity {
    private AdView mAdView;
    Toolbar toolbar;
    AlertDialog dialog;
    String name,number;
    Context c;
    RecyclerView rvList;
    com.satyajiit.sms.msgListAdapter msgListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_read);

        toolbar = findViewById(R.id.toolbar);

        c=this;

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.regis)
                .build();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        setSupportActionBar(toolbar);

        Intent i=getIntent();

        name=i.getStringExtra("name");
        number=i.getStringExtra("phn");

        getSupportActionBar().setTitle(name+" Messages");


         rvList = findViewById(R.id.msgs_rv);




new loader().execute("");
               //here






    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/dis.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private class loader extends AsyncTask<String, Integer, String> {




        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {

            dialog.setCancelable(false);
            dialog.show();



        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {


            String res="";


            List<ListItem> list = new ArrayList();
            ListItem listItem;

            Uri uriSms = Uri.parse("content://sms/inbox");
            final Cursor cursor = c.getContentResolver().query(uriSms,
                    new String[]{"_id", "address", "date", "body"}, null, null, null);




            while (cursor.moveToNext()) {

                if(cursor.getString(3).length()>3)
            if(!(cursor.getString(3).substring(0,4).equals("#@@#"))) continue;

                String date=cursor.getString(2);

                Long timestamp = Long.parseLong(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timestamp);
                Date finaldate = calendar.getTime();


                SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy EEE hh:mm aa");
                String time1 = sdf.format(finaldate);





                if(cursor.getString(1).equals(number)) {

                    String msg = cursor.getString(3);
                    if (msg.length()<4) continue;
                    listItem = new ListItem();


                    listItem.setMessage(msg.substring(4)); //msg

                    listItem.setCount(time1);
                    listItem.setNumber(number);
                    list.add(listItem);


                }




            }
           msgListAdapter = new msgListAdapter(list, c);




            //sss
            return res;
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            rvList.setLayoutManager(new LinearLayoutManager(
                    c));
            rvList.setAdapter(msgListAdapter);


            if (dialog.isShowing()) {
                dialog.dismiss();
            }




        }
    }


}