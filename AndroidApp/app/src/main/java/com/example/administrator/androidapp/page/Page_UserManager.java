package com.example.administrator.androidapp.page;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Message;

public class Page_UserManager extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_manager);

        Message msg = Message.getCurrentMessage();
        TextView account = (TextView)findViewById(R.id.Account);
        account.setText(msg.getUser().getAccount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_manager, menu);
        return true;
    }

    public void edit_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(Page_UserManager.this, Page_UserInformation.class);
        Page_UserManager.this.startActivity(intent);
        Page_UserManager.this.finish();
    }

    public void close_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(Page_UserManager.this, Page_TotalActivity.class);
        Page_UserManager.this.startActivity(intent);
        Page_UserManager.this.finish();
    }

    int focus = 0;


    public void running_Click(View v) {
        if(focus!=0){
            focus = 0 ;
            LinearLayout rn=(LinearLayout) findViewById(R.id.running);
            rn.setBackgroundColor(0xFF50d2c2);
            LinearLayout hi=(LinearLayout) findViewById(R.id.history);
            hi.setBackgroundColor(Color.WHITE);
            LinearLayout ap=(LinearLayout) findViewById(R.id.applying);
            ap.setBackgroundColor(Color.WHITE);
        }
    }

    public void history_Click(View v) {
        if(focus!=1){
            focus = 1 ;
            LinearLayout rn=(LinearLayout) findViewById(R.id.running);
            rn.setBackgroundColor(Color.WHITE);
            LinearLayout hi=(LinearLayout) findViewById(R.id.history);
            hi.setBackgroundColor(0xFF50d2c2);
            LinearLayout ap=(LinearLayout) findViewById(R.id.applying);
            ap.setBackgroundColor(Color.WHITE);
        }

    }

    public void applying_Click(View v) {
        if(focus!=2){
            focus = 2 ;
            LinearLayout rn=(LinearLayout) findViewById(R.id.running);
            rn.setBackgroundColor(Color.WHITE);
            LinearLayout hi=(LinearLayout) findViewById(R.id.history);
            hi.setBackgroundColor(Color.WHITE);
            LinearLayout ap=(LinearLayout) findViewById(R.id.applying);
            ap.setBackgroundColor(0xFF50d2c2);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
