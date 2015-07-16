package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;


public class Page_UserInformation extends ActionBarActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_information);
        user = User.getCurrentUser();
        LoadInformation();
        ImageView iv = (ImageView)findViewById(R.id.imageView13);
        iv.setImageBitmap(ToolClass.resizeBitmap(Cache.getUserAvater(), this, iv.getWidth(), iv.getHeight()));
    }

    private void LoadInformation(){
        loadOne(R.id.Account,"Accout");
        loadOne(R.id.Sex,"Sex");
        loadOne(R.id.Age,"Age");
        loadOne(R.id.Constellation, "Constellation");
        loadOne(R.id.Profession, "Profession");
        loadOne(R.id.LivePlace,"LivePlace");
        loadOne(R.id.Description, "Description");
    }

    private void loadOne(int id,String content){
        TextView tv = (TextView)findViewById(id);
        user.loadInformationToTextView(tv, content);
    }

    public void back_Click(View v) {
        Utils.transPage(this, Page_TotalActivity.class);
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
