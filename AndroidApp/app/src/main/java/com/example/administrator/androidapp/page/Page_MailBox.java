package com.example.administrator.androidapp.page;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

public class Page_MailBox extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_mail_box);

        Utils.setEditView(this,R.id.Mailbox,Current.getUser().getMailbox());

        if(Current.getUser().getIsCheckedMailbox().equals("1")){
            Utils.setButtonVisible(this,R.id.Ok,View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__mail_box, menu);
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


    public void submit_Click(View v){
        String mailbox = Utils.getValueOfEditText(this, R.id.Mailbox);
        if(mailbox!=null){
            MyMessage msg = ToolClass.verify(Current.getUser().getUserID(), mailbox);
            if(msg!=null){
                if(msg.getMess().equals("ok")){
                    Utils.showMessage(this, "验证邮件已发送，请登陆邮箱验证");
                    Utils.backPage(this);
                }
            }else{
                Utils.debugMessage(this,"Page_Mialbox msg为空");
            }
        }
    }
}
