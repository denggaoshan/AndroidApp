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
import com.example.administrator.androidapp.tool.Utils;

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
        Utils.transPage(this, Page_TotalActivity.class);
    }

    private void changeFocus(int i){
        int[] ids = {R.id.running,R.id.history,R.id.applying};

        for(int id:ids){
            LinearLayout layout=(LinearLayout) findViewById(id);
            layout.setBackgroundColor(Color.WHITE);
        }

        LinearLayout rn=(LinearLayout) findViewById(ids[i]);
        rn.setBackgroundColor(0xFF50d2c2);
    }


    public void running_Click(View v) {
       changeFocus(0);
    }

    public void history_Click(View v) {
       changeFocus(1);
    }

    public void applying_Click(View v) {
         changeFocus(2);
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
