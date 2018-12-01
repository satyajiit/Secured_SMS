package com.satyajiit.sms;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;



import static com.satyajiit.sms.InboxFragment.MY_PERMISSIONS_REQUEST_READ_SMS;


public class MainActivity extends AppCompatActivity implements MsgFragment.OnFragmentInteractionListener
        {
    Fragment fragment = null;


    private Toolbar toolbar;

    Boolean permission = false;

    Context c;

    //Change fragments by click nav buttons
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_msg:
                    fragment = MsgFragment.newInstance();
                    break;
                case R.id.navigation_inbox:
                    fragment = InboxFragment.newInstance();

                    break;
                case R.id.navigation_abt:
                    fragment = AbtFragment.newInstance();
                    break;


            }

            //new loader().execute("ss");

            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.commit();
            }
            return true;
        }
    };
    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "fonts/dis.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));



    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getResources().getString(R.string.id));



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = checkSmsPermission();
        }


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, MsgFragment.newInstance());
        fragmentTransaction.commit();

    }
    public boolean checkSmsPermission() {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_SMS)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS,Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS,Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_SMS);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.
                        PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.READ_SMS)
                            == PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                } else {
                    Toast.makeText(this,
                            "Permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }


        }

    }
}
