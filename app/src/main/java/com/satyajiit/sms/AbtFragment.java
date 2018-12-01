package com.satyajiit.sms;




import android.content.Context;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;


import android.support.v7.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;



public class AbtFragment extends Fragment {

    private AdView mAdView;
    private RewardedVideoAd mRewardedVideoAd;
    CardView vdo,contact,more;
    TextView wt;

    public static AbtFragment newInstance() {
        return new AbtFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About - SECURE SMS");
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_abt, container, false);

        mAdView = view.findViewById(R.id.adView);

        vdo=view.findViewById(R.id.vdo);
        wt=view.findViewById(R.id.watch);
        contact=view.findViewById(R.id.contact);
        more=view.findViewById(R.id.more);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mRewardedVideoAd.setRewardedVideoAdListener(rewardedVideoAdListener);

        loadRewardedVideoAd();

        vdo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                else  Toast.makeText(getActivity(),"Sorry No ads available now..",Toast.LENGTH_SHORT).show();

            }
        });

        vdo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
                else  Toast.makeText(getActivity(),"Sorry No ads available now..",Toast.LENGTH_SHORT).show();

            }
        });



        contact.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:satyajiit0@gmail.com")));
            }
        });

        more.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://satyajiit.xyz")));
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
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

    public interface OnFragmentInteractionListener {
    }




    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLoaded() {

            vdo.setEnabled(true);
            wt.setEnabled(true);
            Toast.makeText(getActivity(), "A Video Ad is available , please consider watching!!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRewardedVideoAdOpened() {
            //Toast.makeText(getActivity(), "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoStarted() {
           // Toast.makeText(getActivity(), "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            loadRewardedVideoAd();
        }

        @Override
        public void onRewarded(RewardItem reward) {
            Toast.makeText(getActivity(), "Thank You Dear for Watching!!", Toast.LENGTH_LONG).show();
            // Reward the user.
        }

        @Override
        public void onRewardedVideoAdLeftApplication() {
            //Toast.makeText(getActivity(), "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {
            //Toast.makeText(getActivity(), "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardedVideoCompleted() {
            vdo.setEnabled(false);
            wt.setEnabled(false);
        }
    };

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getResources().getString(R.string.advdo),
                new AdRequest.Builder().build());
    }






}