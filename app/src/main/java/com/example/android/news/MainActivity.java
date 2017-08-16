package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    Context context=this;
    ListView lv;
    String myurl="https://newsapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=YOUR_KEY_HERE";
    MyAdapter adapter;
    ArrayList<String>title=new ArrayList<String>();
    ArrayList<String>description=new ArrayList<String>();
    ArrayList<String>imageurl=new ArrayList<String>();
    ArrayList<String>newsurl=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv= (ListView) findViewById(R.id.listview);
        adapter=new MyAdapter(this,title,imageurl);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new JSONTask().execute(myurl);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //view-- it is a view which is clicked
        //position-- Index of item clicked(0 based)
       /* l1.add("Funday");
        l2.add(R.drawable.d11);
        adapter=new MyAdapter(this,l1,l2);
        lv.setAdapter(adapter);*/
        Intent i=new Intent(MainActivity.this,Details.class);
        i.putExtra("image",imageurl.get(position));
        i.putExtra("des",description.get(position));
        i.putExtra("head",title.get(position));
        i.putExtra("url",newsurl.get(position));
        startActivity(i);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this,"haha",Toast.LENGTH_SHORT).show();
        return false;
    }


    public class JSONTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (Exception e) {

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                title.clear();
                description.clear();
                imageurl.clear();
                newsurl.clear();
                JSONObject jobject=new JSONObject(s);
                JSONArray jarray=jobject.getJSONArray("articles");
                int len=jarray.length();
                for(int i=0;i<len;i++){
                    JSONObject j2=jarray.getJSONObject(i);
                    title.add(j2.getString("title"));
                    description.add(j2.getString("description"));
                    imageurl.add(j2.getString("urlToImage"));
                    newsurl.add(j2.getString("url"));
                }
                adapter=new MyAdapter(context,title,imageurl);
                lv.setAdapter(adapter);
                System.gc();

            }
            catch (Exception e){

            }

        }
    }
}



class MyAdapter extends ArrayAdapter<String>{
    Context context;
    ArrayList<String>images;
    ArrayList <String>heading;
    MyAdapter(Context c,ArrayList<String> desc,ArrayList<String> imgs){
        super(c,R.layout.single_row,R.id.textView,desc);
        this.context=c;
        this.images=imgs;
        this.heading=desc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // HERE VIEW HOLDING SHOULD BE USED SEE SLIDENERD VIDEO(93 or close)
        View row;
        MyViewHolder holder=null;
        row=convertView;
        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row, parent, false);
            holder=new MyViewHolder(row);
            row.setTag(holder);
        }
        else{
            holder= (MyViewHolder) row.getTag();
        }
        //ImageView myimage= (ImageView) row.findViewById(R.id.imageView);
        //TextView title= (TextView) row.findViewById(R.id.textView);
        try {
           /* URL url = new URL(images.get(position));
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            myimage.setImageBitmap(bmp);*/
            Picasso.with(context).load(images.get(position)).transform(new CircleTransform()).into(holder.myimage);
        }
        catch (Exception e){
            holder.myimage.setImageResource(R.drawable.d11);
        }

        //myimage.setImageResource(images.get(position));
        holder.title.setText(heading.get(position));
        System.gc();
        return row;
    }
}
