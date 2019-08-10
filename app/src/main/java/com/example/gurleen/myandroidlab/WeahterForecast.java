package com.example.gurleen.myandroidlab;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WeahterForecast extends Activity {

    ProgressBar progressBar;
    TextView currentTemp;
    TextView maxTemp;
    TextView minTemp;
    ImageView weatherImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weahter_forecast);
         progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        currentTemp = (TextView) findViewById(R.id.textViewCurrentTemprature);
        maxTemp = findViewById(R.id.textViewMaxTemprature);
        minTemp = findViewById(R.id.textViewMinTemprature);
        weatherImage = findViewById(R.id.imageViewWeather);

        ForecastQuery forecastQuery = new ForecastQuery();
        forecastQuery.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

    }

    public class ForecastQuery extends AsyncTask<String,Integer,String>{
        //public String urlString="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
       public  String min;
       public String max;
       public String currentTemprature;
       public Bitmap image;
       String iconName;
        @Override
        protected String doInBackground(String... strings)
        {

            URL url = null;
            try {
                url = new URL(strings [0]);
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream inputStream=  conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(inputStream,null);
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    if(parser.getName().equals("temperature")){
                        currentTemprature=parser.getAttributeValue(null,"value");
                        publishProgress(25);
                        min= parser.getAttributeValue(null,"min");
                        publishProgress(50);
                        max=parser.getAttributeValue(null,"max");
                        publishProgress(75);

                    }
                    if(parser.getName().equals("weather"))
                    {
                        iconName = parser.getAttributeValue(null, "icon");
                        String imageFile = iconName + ".png";
                        if (fileExistence(imageFile)) {


                            FileInputStream fis = null;
                            try {
                                fis = openFileInput(imageFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            image  = BitmapFactory.decodeStream(fis);
                        } else
                        {
                          URL ImageURL = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                             image  = HttpUtils.getImage(ImageURL);
                            FileOutputStream outputStream = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();

                        }
                        publishProgress(100);
                    }
                }
                } catch (IOException e1) {
                e1.printStackTrace();
                } catch (XmlPullParserException e2) {
                e2.printStackTrace();
            }


         return  null;

        }
       @Override
        protected void onProgressUpdate(Integer...value){
           progressBar.setVisibility(View.VISIBLE);
           progressBar.setProgress(value[0]);

       }
       @Override
       protected void onPostExecute(String string){
            progressBar.setVisibility(View.INVISIBLE);
            currentTemp.setText("Current Temprature : "+ currentTemprature);
            maxTemp.setText("Max TEmpature : "+max);
            minTemp.setText("Min Temprature : "+ min);
            weatherImage.setImageBitmap(image);


       }
    }

    public boolean fileExistence(String fileName){
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }

    public static class HttpUtils{

        public static Bitmap getImage(URL imageURL) {

            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) imageURL.openConnection();
                conn.connect();
                int responsecode= conn.getResponseCode();
                if(responsecode==200){
                    
                    return BitmapFactory.decodeStream(conn.getInputStream());
                }
                else {
                    return null;
                }

            } catch (Exception e) {
                return  null;

            }finally {
                if(conn!=null){
                    conn.disconnect();
                }
            }
            }

        }
}
