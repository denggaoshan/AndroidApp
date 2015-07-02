package com.example.administrator.androidapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class UserInformation extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        LoadInformation();
    }

    private void LoadInformation(){
        User user = User.getCurrentUser();

        TextView tv = (TextView)findViewById(R.id.Account);
        user.loadInformationToTextEdit(tv ,"Account");

        tv = (TextView)findViewById(R.id.Sex);
        user.loadInformationToTextEdit(tv ,"Sex");

        tv = (TextView)findViewById(R.id.Age);
        user.loadInformationToTextEdit(tv ,"Age");

        tv = (TextView)findViewById(R.id.Constellation);
        user.loadInformationToTextEdit(tv ,"Constellation");

        tv = (TextView)findViewById(R.id.Profession);
        user.loadInformationToTextEdit(tv ,"Profession");

        tv = (TextView)findViewById(R.id.LivePlace);
        user.loadInformationToTextEdit(tv ,"LivePlace");

        tv = (TextView)findViewById(R.id.Description);
        user.loadInformationToTextEdit(tv ,"Description");

    }

    //·µ»ØµÇÂ½½çÃæ
    public void back_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(UserInformation.this, UserManagerActivity.class);
        UserInformation.this.startActivity(intent);
        UserInformation.this.finish();
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
