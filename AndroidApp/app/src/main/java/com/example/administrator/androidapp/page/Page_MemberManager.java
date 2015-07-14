package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.MyActivity;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;

public class Page_MemberManager extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_member_manager);

        loadActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__member_manager, menu);
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

    private MyActivity currentActivity;

    private void loadActivity(){
        currentActivity  = MyActivity.getCurrentActivity();
        Message msg = ToolClass.getApplication(User.getCurrentUser().getUserID(), currentActivity.getActivityID());
        if(msg==null){
            Utils.debugMessage(this,"msg = null");
        }
        User[] users = msg.getUsers();
    };
}
