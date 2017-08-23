package com.application.robat.postage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String postsURL ="http://apps.nd0.pl/mock/posts";
    private static String content ="Null";

    TextView twDescription;
    TextView twName;
    TextView twTimestamp;
    TextView twNick;
    TextView twSurename;
    TextView twTitle;
    ImageView ivImageBig;


    public void getPost(String id) {
        String postURL = "http://apps.nd0.pl/mock/post/" + id;
        String[] parts = content.split(",|:|\\[|\\{|\\}|\\]|\"");
        String name="";
        String surename="";
        String nick="";
        String title="";
        String description="";
        String timestamp="1482962353";
        String imageUrl="";

        twDescription = (TextView)findViewById(R.id.tvDescBig);
        twName =(TextView)findViewById(R.id.tvNameBig);
        twSurename =(TextView)findViewById(R.id.tvSurenameBig);
        twNick =(TextView)findViewById(R.id.tvNickBig);
        twTimestamp = (TextView)findViewById(R.id.tvTimestampBig);
        twTitle =(TextView)findViewById(R.id.tvTitleBig);
        ivImageBig =(ImageView)findViewById(R.id.imageBig);

        String[] data = {content, postURL};

        new DataAsyncTask().execute(data);

        for (int i = 0; i < parts.length; i++) {
            System.out.println(i + " " + parts[i]);
            if (i == 9) {
                name = parts[i];
            }
            if (i == 15) {
                surename = parts[i];
            }
            if (i == 21) {
                nick = parts[i];
            }
            if (i == 28) {
                description = parts[i];
            }
            if (i == 42) {
                imageUrl = parts[i];
            }
            if (i == 48) {
                title = parts[i];
            }
            if (i == 57) {
                //timestamp = parts[i];
                timestamp="1482962353";
            }
        }

        long time = Long.valueOf(timestamp).longValue();
        System.out.println(timestamp);
        Date date = new java.util.Date(time);
        String timestampDate = date.toString();

        twDescription.setText(description);
        twName.setText(name);
        twSurename.setText(surename);
        twNick.setText(nick);
        twTitle.setText(title);
        twTimestamp.setText(timestampDate);

        new DownloadImageTask2((ivImageBig)).execute("http:" + imageUrl);

    }

    public void addPosts(){
        String[] parts = content.split(",|:|\\[|\\{|\\}|\\]|\"");
        List<String> nicks = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();
        List<String> descriptions = new ArrayList<String>();
        List<String> timestamps = new ArrayList<String>();
        List<String> imagesUrls = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();

        int counter = 0;
        int group = 1;

        for(int i = 0; i< parts.length;i++){
            // System.out.println(i+" "+parts[i]);
            if(i == 22+counter){
                nicks.add(parts[i]);
                System.out.println(i+" "+parts[i]);
            }
            if(i == 42+counter){
                titles.add(parts[i]);
                System.out.println(i+" "+parts[i]);
            }
            if(i == 36+counter){
                descriptions.add(parts[i]);
                System.out.println(i+" "+parts[i]);
            }
            if(i == 51+counter){
                timestamps.add(parts[i]);
                System.out.println(i+" "+parts[i]);
                counter=counter+52;
                group++;
            }
            if(i == 30+counter){
                imagesUrls.add(parts[i]);
                System.out.println(i+" "+parts[i]);
            }
            if(i == 47+counter){
                ids.add(parts[i]);
                System.out.println(i+" "+parts[i]);
            }

        }

        ArrayList<Row> arrayOfPosts = new ArrayList<Row>();

        // Create the adapter to convert the array to views
        PostsAdapter adapter = new PostsAdapter(this, arrayOfPosts);

        for(int i=0; i < nicks.size(); i++) {
            Row newRow = new Row(nicks.get(i), titles.get(i), descriptions.get(i), timestamps.get(i), imagesUrls.get(i), ids.get(i));
            adapter.add(newRow);
        }
        //Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] data = {content,postsURL};

        new StartAsyncTask().execute(data);



    }

    public class PostsAdapter extends ArrayAdapter<Row>{

        public PostsAdapter(Context context, ArrayList<Row> rows) {
            super(context, 0, rows);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Row row = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            }

            TextView tvDescRow = (TextView) convertView.findViewById(R.id.tvDescRow);
            TextView tvTimestampRow = (TextView) convertView.findViewById(R.id.tvTimestampRow);
            TextView tvNameRow = (TextView) convertView.findViewById(R.id.tvNickRow);
            TextView tvTitleRow = (TextView) convertView.findViewById(R.id.tvTitleRow);
            ImageButton bImage = (ImageButton) convertView.findViewById(R.id.imageButton);

            new DownloadImageTask((bImage)).execute("http:"+row.imageUrl);
            bImage.setTag(position);

            bImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer) v.getTag();
                    // Access the row position here to get the correct data item
                    Row row = getItem(position);
                    // Do what you want here...
                    System.out.println(row.RowID);
                    getPost(row.RowID);

                }
            });



            long timestamp = Long.valueOf(row.timestamp).longValue();
            System.out.println(timestamp);
            Date date = new java.util.Date(timestamp);
            String data = date.toString();


            tvDescRow.setText(row.description);
            tvTimestampRow.setText(data);
            tvNameRow.setText(row.name);
            tvTitleRow.setText(row.title);

            //bImage.setImageDrawable(URLConnectionReader.LoadImageFromWebOperations("http:"+row.imageUrl));


            return convertView;
        }
    }

    private class StartAsyncTask extends AsyncTask<String, String, String>{

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // działania po wykonaniu zdania w doInBackground
            // ta metoda działa w UI, więc możesz zmieniać różne elementy!

            //twDescription.setText(result);
            addPosts();


        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[1];
            content = params[0];

            try {
                content = URLConnectionReader.getText(url);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return content;
        }
    }

    private class DataAsyncTask extends AsyncTask<String, String, String>{

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // działania po wykonaniu zdania w doInBackground
            // ta metoda działa w UI, więc możesz zmieniać różne elementy!

            //twDescription.setText(result);



        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[1];
            content = params[0];

            try {
                content = URLConnectionReader.getText(url);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return content;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


    private class DownloadImageTask2 extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask2(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}


