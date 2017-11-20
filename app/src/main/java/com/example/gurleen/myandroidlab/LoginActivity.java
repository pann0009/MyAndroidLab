package com.example.gurleen.myandroidlab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static final  String MY_PRFS_NAME = "MyPrefsFile";
    SharedPreferences sharedPref;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPref = getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.getString("DefaultEmail", "").equals("")){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("DefaultEmail", "email@domain.com");
            editor.commit();
        }
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Button button =findViewById(R.id.button2);
        editText = (EditText) findViewById(R.id.textEmailAddress);
        editText.setText(sharedPref.getString("DefaultEmail", "no email"));

        if(!sharedPref.getString("email", "").equals("")){
            editText.setText(sharedPref.getString("email", "no email"));
        }



       //String text = editText.getText().toString();
       // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
        //long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);

       // SharedPreferences.Editor editor = getSharedPreferences(MY_PRFS_NAME, MODE_PRIVATE).edit();
        //editor.putString(editText.getText().toString(), null);
        //editor.apply();

        //SharedPreferences sharedPreferences = null;
//        sharedPreferences.getString("DefaultEmail","email@domain.com");

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!editText.getText().toString().equals("")){
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("email", editText.getText().toString());
                    editor.commit();
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




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
