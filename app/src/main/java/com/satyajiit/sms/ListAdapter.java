package com.satyajiit.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.myHolder>{

    private List<ListItem> list;
    private Context mContext;
    public ListAdapter(List<ListItem> list, Context mContext){
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.msg_list, null);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {
        final ListItem listItem = list.get(position);

        Typeface typeface =  Typeface.createFromAsset(mContext.getAssets(),"fonts/Cav.ttf");

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color2 = generator.getColor(listItem.getMessage());

        TextDrawable drawable1;

        final String name=getContactName(mContext,listItem.getNumber());

final String p=listItem.getNumber();





        if(name!=null) {
            holder.ph_no.setText(name);
        }
        else  holder.ph_no.setText(listItem.getNumber());

        if(name!=null) drawable1 = TextDrawable.builder()
                .buildRound(String.valueOf(name.charAt(0)), color2);

        else if(Character.isDigit(listItem.getNumber().charAt(0))||!Character.isAlphabetic(listItem.getNumber().charAt(0)))
        drawable1 = TextDrawable.builder()
                .buildRound("#", color2);

        else    drawable1 = TextDrawable.builder()
                .buildRound(String.valueOf(listItem.getNumber().charAt(0)), color2);

        holder.ph_no.setTypeface(typeface);
        holder.msg.setTypeface(typeface);
        holder.msg.setText(listItem.getMessage());
        holder.vi.setImageDrawable(drawable1);


            TextDrawable drawable2 = TextDrawable.builder()
                    .buildRound(listItem.getCount(), Color.parseColor("#028678"));

            holder.count.setImageDrawable(drawable2);

final String n=listItem.getNumber();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i=new Intent(mContext,ReadSmsAct.class);
                i.putExtra("phn",n);
                if(name!=null)
                i.putExtra("name",name);
                else i.putExtra("name",p);
                mContext.startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView ph_no;
        TextView msg;
        ImageView vi,count;

        public myHolder(View itemView) {
            super(itemView);
            ph_no = itemView.findViewById(R.id.ph_no);
            msg =  itemView.findViewById(R.id.msg);
            vi=itemView.findViewById(R.id.image_view);
            count=itemView.findViewById(R.id.count);
            cv=itemView.findViewById(R.id.card_view);
        }
    }
    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
}