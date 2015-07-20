package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;

public class Page_Information_Others extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_others_information);

        loadInformation();
    }
    User user ;

    private void loadInformation() {
        user = User.getOtherUser();
        if(user!=null){
            LoadUserInformation(user);
        }else{
            Utils.debugMessage(null,"user为空");
        }
    }

    private void LoadUserInformation(User user){

        int[] ids={R.id.NickName,R.id.Sex,R.id.Age,R.id.Constellation,R.id.Profession,R.id.LivePlace,
                R.id.Description,R.id.Phone, R.id.Mailbox, R.id.good,
        };
        String[] attribute={"NickName","Sex","Age","Constellation","Profession","LivePlace","Description","Phone","Mailbox","good"};

        Utils.loadUserInformation(this,user,ids,attribute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__others_information, menu);
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

    //发私信
    public void message_Click(View v)
    {
        Utils.transPage(this, Page_SendMessage.class);
    }

    //点赞
    public void good_Click(View v)
    {
        String str = User.getCurrentUser().createGood(User.getOtherUser().getUserID());
        Utils.showMessage(this,str);
    }

}
