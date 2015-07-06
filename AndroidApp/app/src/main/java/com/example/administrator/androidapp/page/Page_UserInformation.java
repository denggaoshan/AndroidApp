package com.example.administrator.androidapp.page;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Message;


public class Page_UserInformation extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_information);

        LoadInformation();
    }

    private void LoadInformation(){
        Message msg = Message.getCurrentMessage();

        TextView tv = (TextView)findViewById(R.id.Account);
        msg.getUser().loadInformationToTextEdit(tv, "Account");

        tv = (TextView)findViewById(R.id.Sex);
        msg.getUser().loadInformationToTextEdit(tv, "Sex");

        tv = (TextView)findViewById(R.id.Age);
        msg.getUser().loadInformationToTextEdit(tv, "Age");

        tv = (TextView)findViewById(R.id.Constellation);
        msg.getUser().loadInformationToTextEdit(tv, "Constellation");

        tv = (TextView)findViewById(R.id.Profession);
        msg.getUser().loadInformationToTextEdit(tv, "Profession");

        tv = (TextView)findViewById(R.id.LivePlace);
        msg.getUser().loadInformationToTextEdit(tv, "LivePlace");

        tv = (TextView)findViewById(R.id.Description);
        msg.getUser().loadInformationToTextEdit(tv ,"Description");

    }


    public void back_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(Page_UserInformation.this, Page_UserManager.class);
        Page_UserInformation.this.startActivity(intent);
        Page_UserInformation.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_information, menu);
        return true;
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
