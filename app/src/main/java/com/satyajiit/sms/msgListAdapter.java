package com.satyajiit.sms;



import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.scottyab.aescrypt.AESCrypt;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

public class msgListAdapter extends RecyclerView.Adapter<msgListAdapter.myHolder> {
    String key;
    String m;

    private List<ListItem> list;
    private Context mContext;
String msg;
    public msgListAdapter(List<ListItem> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.msg_read, null);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        final ListItem listItem = list.get(position);

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Cav.ttf");


        holder.msg.setTypeface(typeface);
        msg=listItem.getMessage();
        holder.msg.setText(msg);
        holder.dt.setText("\n"+listItem.getCount());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog diaBox = AskOption();
                diaBox.show();

            }
        });

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(mContext);
                View mView = layoutInflaterAndroid.inflate(R.layout.pass_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Decrypt", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                key=String.valueOf(userInputDialogEditText.getText()); //get the pass from user
                                init();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });


        holder.dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(mContext);
                View mView = layoutInflaterAndroid.inflate(R.layout.pass_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Decrypt", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                key=String.valueOf(userInputDialogEditText.getText()); //get the pass from user
                                init();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder {

        // CardView cv;
        TextView dt;
        TextView msg, del,dec;
        CardView click;
        // ImageView vi,count;

        public myHolder(View itemView) {
            super(itemView);
            dt = itemView.findViewById(R.id.date);
            del = itemView.findViewById(R.id.delete);
            msg = itemView.findViewById(R.id.msg2);
            dec=itemView.findViewById(R.id.decr);
            click=itemView.findViewById(R.id.card_view);
        }
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this.mContext)
                //set message, title, and icon
                .setTitle("Confirmation")

                .setMessage("Do you want to Delete this message? You will be taken to your default sms app where you can delete it.")
                //.setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);

                        intent.addCategory(Intent.CATEGORY_DEFAULT);

                        intent.setType("vnd.android-dir/mms-sms");

                        mContext.startActivity(intent);

                       // Toast.makeText(mContext, id+ " Delete Success!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }

                })


                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;


    }

public void init(){
    try {
        m=base64(AESCrypt.decrypt(String.valueOf(key),base64(msg)));
        ss();
    } catch (GeneralSecurityException e) {
        msg("INVALID PASSWORD ENTERED!!");
        e.printStackTrace();
    }
}
    public void msg(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }



    public String base64(String msg){
        String text="";
        byte[] data = Base64.decode(msg, Base64.DEFAULT);
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void ss(){

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(mContext);
        View mView = layoutInflaterAndroid.inflate(R.layout.dec_msg, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
        alertDialogBuilderUserInput.setView(mView);



        final EditText userInputDialogEditText = mView.findViewById(R.id.dmsg);
        userInputDialogEditText.setText(m);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Copy & Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("copied", m);
                        clipboard.setPrimaryClip(clip);
                        msg("Successfully Copied...");

                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

    }
}