package com.example.gurleen.myandroidlab;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gurleen.myandroidlab.R;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    ListView listView;
    EditText editText;
    Button sendButton;
    ChatAdapter messageAdapter;
    ChatAdapterHelper chatAdapterHelper;
    ArrayList<String> arrayList;
    SQLiteDatabase db;
    Cursor c;
    private final String ACTIVITY_NAME = "ChatWindow";
    private  boolean Tablet;
    public static String message;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Tablet = findViewById(R.id.frameLayout600)!= null;

        chatAdapterHelper = new ChatAdapterHelper(this);
        db = chatAdapterHelper.getWritableDatabase();

        arrayList = new ArrayList<String>();
         c = db.rawQuery("select * from " + ChatAdapterHelper.DATABASE_NAME, new String[]{});
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + c.getColumnCount());
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "select * from " + ChatAdapterHelper.DATABASE_NAME
                    + c.getString(c.getColumnIndex(ChatAdapterHelper.KEY_MESSAGE)));
            arrayList.add(c.getString(c.getColumnIndex(ChatAdapterHelper.KEY_MESSAGE)));
            c.moveToNext();
        }
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editTextChatWindow);
        sendButton = findViewById(R.id.sendButton);
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String msg = editText.getText().toString();
                if (!msg.isEmpty()) {
                    arrayList.add(msg);
                    ContentValues cValues = new ContentValues();
                    cValues.put(ChatAdapterHelper.KEY_MESSAGE, msg);
                    db.insert(ChatAdapterHelper.DATABASE_NAME, "NullColumnName", cValues);
                    editText.setText("");
                    messageAdapter.notifyDataSetChanged();
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i(ACTIVITY_NAME, "item from the list view has been clicked");
                if(Tablet){
                    // if its in tablet mode you need your sw600dp

                }else{
                    // in the phone mode
                    Bundle bundle = new Bundle();
                    c.moveToPosition(position);
                    bundle.putString(message, c.getString(c.getColumnIndex(ChatAdapterHelper.KEY_MESSAGE)));
                    bundle.putString(id, c.getString(c.getColumnIndex(ChatAdapterHelper.KEY_ID)));
                    Intent intent = new Intent(ChatWindow.this, MessageDetail.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);


                }

            }
        });
    }
    protected void onActivityResult(int request, int result, Intent data){
        Log.i(ACTIVITY_NAME, "in onActivityResults");


    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return arrayList.size();
        }

        public String getItem(int position) {
            return arrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;


        }
        public long getItemId(int position){
            return  position;
        }


    }
    public void onDestroy()
    {

        super.onDestroy();
        if(c!= null)
        {
            c.close();
        }

        if(chatAdapterHelper!= null)
        {
            chatAdapterHelper.close();
        }
    }
}
