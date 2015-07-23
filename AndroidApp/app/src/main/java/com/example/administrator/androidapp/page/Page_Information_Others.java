package com.example.administrator.androidapp.page;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Current;
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
        user = Current.getOtherUser();
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
        String[] attribute={"NickName","Sex","Age","Constellation","Profession","LivePlace","Description","Phone","Mailbox","Good"};

        Utils.loadUserInformation(this,user,ids,attribute);

        Cache.loadImg(this,user.getAvatar(),R.id.image);
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
        Page_SendMessage.setMessageType("1");
        Utils.transPage(this, Page_SendMessage.class);
    }

    //点赞
    public void good_Click(View v)
    {
        String str = Current.getUser().createGood(Current.getOtherUser());
        if(str.equals("ok")){
            //点赞成功
            String tmp  = ((TextView)findViewById(R.id.good)).getText().toString();
            if(tmp!=null){
                String good = String.valueOf(1 + Integer.parseInt(tmp));
                Cache.updateLoadAllUsers(Current.getActivity().getActivityID());
                Utils.setTextView(this,R.id.good,good);
                Utils.showMessage(this,str);
            }else{
                Utils.showMessage(this,"good为空");
            }

        }else{
            Utils.showMessage(this,str);
        }
    }

}
