package com.example.gurleen.myandroidlab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


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
    }
    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode==10){
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
        }
        if(requestCode==Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
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
