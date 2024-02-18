package com.example.as.doctorh;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TipsAdapter extends ArrayAdapter<Tips> {
    private int resourceId;

    public TipsAdapter(@NonNull Context context, int textViewResourceId, List<Tips> objects)  {
        super( context,  textViewResourceId, objects );
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Tips tips=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate( resourceId,parent,false);
        ImageView tipsImage =(ImageView)view.findViewById(R.id.image);
        TextView  tipsName=(TextView)view.findViewById( R.id.title );
        tipsImage.setImageResource( tips.getImageId() );
        tipsName.setText( tips.getName() );
        return view;
    }
}
