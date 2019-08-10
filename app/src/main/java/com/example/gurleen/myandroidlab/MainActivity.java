package com.example.gurleen.myandroidlab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends Activity {
    protected static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
       Button button =  findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListItemActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        Button startChat = findViewById(R.id.chatbutton);
        startChat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ChatWindow.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            }
        });
        Button buttonForWeatherForecast = findViewById(R.id.buttonForWeatherForecast);
        buttonForWeatherForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, WeahterForecast.class);
                startActivity(intent);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == 10) && (resultCode == Activity.RESULT_OK)) {
            String message = data.getStringExtra("Response");
            String response = "ListItemsActivity passed: My information to share " + message;
            Toast toast = Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG); //this is the ListActivity
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            toast.show();


        }


    }
    public void onResume() {

        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    public void	onStart() {

        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    public void onPause() {

        super.onPause();
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }
    public void onStop() {

        super.onStop();
    }
    public void onDestroy() {

        super.onDestroy();
    }


}
