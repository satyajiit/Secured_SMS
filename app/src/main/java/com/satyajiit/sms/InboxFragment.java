package com.satyajiit.sms;





import android.app.AlertDialog;

import android.content.Context;



import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class InboxFragment extends Fragment {


    private AdView mAdView;

    AlertDialog dialog;
    String num;
    List<ListItem> list;
    RecyclerView rvList;
    com.satyajiit.sms.ListAdapter listAdapter;

    public static InboxFragment newInstance() {
        return new InboxFragment();
    }



    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 99;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Inbox - SECURE SMS");
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inbox, container, false);


         rvList = view.findViewById(R.id.rvList);


        dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setTheme(R.style.regis)
                .build();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //here
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

                new loader().execute("");



                return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "fonts/Cav.ttf");
        fontChanger.replaceFonts((ViewGroup) this.getView());


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();


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








                    list = new ArrayList();
                    ListItem listItem;

                    Uri uriSms = Uri.parse("content://sms/inbox");
                    final Cursor cursor = getActivity().getContentResolver().query(uriSms,
                            new String[]{"_id", "address", "date", "body"}, null, null, null);


                    String nums = "";
                    int ct = 0;

                    while (cursor.moveToNext()) {
                        String address = cursor.getString(1);




                        String msg = cursor.getString(3);

                        if(msg.length()>3)
                        { if(!(msg.substring(0,4).equals("#@@#"))) continue; }
                       else continue;

                        listItem = new ListItem();

                        if (!nums.contains(address)) nums = nums + address;
                        else continue;
                        listItem.setNumber(address);
                        if (msg.length() > 12)
                            msg = msg.substring(4, 16) + "...";

                        listItem.setMessage(msg); //msg


                        num = address;

                        final Cursor cursor2 = getActivity().getContentResolver().query(uriSms,
                                new String[]{"_id", "address", "date", "body"}, null, null, null);


                        while (cursor2.moveToNext()) {

                            String address2 = cursor2.getString(1);
                            String m=cursor2.getString(3);


                            if (num.equals(address2))
                                if(m.length()>3)
                           if( m.substring(0,4).equals("#@@#")) ct++;


                        }




                        listItem.setCount(String.valueOf(ct));

                        ct = 0;


                        list.add(listItem);

                    }





            listAdapter = new ListAdapter(list, getActivity().getApplicationContext());








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

            rvList.setAdapter(listAdapter);
            rvList.setLayoutManager(new LinearLayoutManager(
                    getActivity().getBaseContext()));


            if (dialog.isShowing()) {
                dialog.dismiss();
            }



        }
    }


}
