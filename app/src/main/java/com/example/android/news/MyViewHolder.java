package com.example.android.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gaurav on 27/7/17.
 */

public class MyViewHolder {
    ImageView myimage;
    TextView title;
    public MyViewHolder(View v){
        myimage= (ImageView) v.findViewById(R.id.imageView);
        title= (TextView) v.findViewById(R.id.textView);
    }
}
