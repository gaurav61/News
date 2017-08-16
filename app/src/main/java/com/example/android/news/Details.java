package com.example.android.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity implements View.OnClickListener{
    TextView mytextview,mytextview2;
    ImageView myimageview;
    Intent i;
    Button web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mytextview= (TextView) findViewById(R.id.description);
        mytextview2= (TextView) findViewById(R.id.head);
        myimageview=(ImageView)findViewById(R.id.picture);
        web= (Button) findViewById(R.id.webbutton);
        i=getIntent();
        String head=i.getStringExtra("head");
        SpannableString content = new SpannableString(head);
        content.setSpan(new UnderlineSpan(), 0, head.length(), 0);
        Picasso.with(this).load(i.getStringExtra("image")).into(myimageview);
        mytextview2.setText(content);
        mytextview.setText(i.getStringExtra("des"));
        web.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i2=new Intent(Details.this,WebViewActivity.class);
        i2.putExtra("url",i.getStringExtra("url"));
        startActivity(i2);
    }
}
