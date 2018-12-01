package com.satyajiit.sms;




import android.content.Context;


import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;


import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.scottyab.aescrypt.AESCrypt;

import java.io.UnsupportedEncodingException;


public class MsgFragment extends Fragment {

TextView mTextView;
EditText msg,number,pwd;
Button submit;

    private AdView mAdView;



    public static MsgFragment newInstance() {
        return new MsgFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create - SECURE SMS");
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_msg, container, false);

        mTextView=view.findViewById(R.id.ct);
        msg=view.findViewById(R.id.message);
        submit=view.findViewById(R.id.submit);
        number=view.findViewById(R.id.phone);
        pwd=view.findViewById(R.id.pwd);
        msg.addTextChangedListener(mTextEditorWatcher);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);






        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(msg.length()<10) msg("LENGTH OF MESSAGE IS TOO SHORT!!");
                else {
                    if (number.length() < 10) msg("INVALID MOBILE NUMBER ENTERED!!");
                    else if(pwd.length()<6) msg("PASSWORD LENGTH IS TOO SHORT!!");
                    else {
                        //all inputs correct lets send the sms
                        try {
                            SmsManager smsManager = SmsManager.getDefault();

                            String encryptedMsg=base64(String.valueOf(msg.getText()));

                            encryptedMsg = AESCrypt.encrypt(String.valueOf(pwd.getText()), encryptedMsg);

                            encryptedMsg=base64(encryptedMsg);

                            smsManager.sendTextMessage(String.valueOf(number.getText()), null, "#@@#"+encryptedMsg, null, null);

                            //msg("#@@#"+encryptedMsg); //encrypted msg

                            //to identify the msg as enc one ..we will add something as symbol

                           //msg(base64d(AESCrypt.decrypt(String.valueOf(pwd.getText()),base64d(encryptedMsg)))); //decrypted msg

                            msg("Message Sent Successfully");


                        } catch (Exception ex) {
                           msg("An error Occurred While sending the sms. #001");
                            ex.printStackTrace();
                        }

                    }
                }
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


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(String.valueOf(s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };


public void msg(String msg){
    Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
}


public String base64(String msg){
    byte[] data = new byte[0];
    try {
        data = msg.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
        msg("ENCRYPTION ERROR CODE #002");
        e.printStackTrace();
    }
    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
    return base64;
}




}