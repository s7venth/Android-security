package com.esgi.security;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView number;
    TextView message;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String num = bundle.getString("number");
            String messages = bundle.getString("message");
            message.setText(messages);
            number.setText(num);
        }
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        number = (TextView)findViewById(R.id.num);
        message = (TextView)findViewById(R.id.message);
    }

    /*
        telnet localhost 5554
        sms send senderPhoneNumber textmessage
     */


}
