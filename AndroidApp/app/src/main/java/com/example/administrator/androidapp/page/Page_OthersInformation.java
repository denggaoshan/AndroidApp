package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;

public class Page_OthersInformation extends ActionBarActivity {

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
        loadOne(user,R.id.NickName,"NickName");
        loadOne(user,R.id.Sex,"Sex");
        loadOne(user,R.id.Age,"Age");
        loadOne(user,R.id.Constellation, "Constellation");
        loadOne(user, R.id.Profession, "Profession");
        loadOne(user, R.id.LivePlace, "LivePlace");
        loadOne(user, R.id.Description, "Description");
    }

    private void loadOne(User user,int id,String content){
        TextView tv = (TextView)findViewById(id);
        if(tv!=null){
            user.loadInformationToTextView(tv,content);
        }else{
            Utils.debugMessage(Page_OthersInformation.this,"没有找到"+content);
        }
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

    public void back_Click(View v)
    {
        Utils.transPage(this, Utils.getBeforeActivity().getClass());
    }

}
