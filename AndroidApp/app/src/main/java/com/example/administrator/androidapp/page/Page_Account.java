package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.tool.Utils;

public class Page_Account extends ActionBarActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_account);

        loadUserInformation();
    }

    private void loadUserInformation() {
        currentUser = User.getCurrentUser();
        LoadInformation();

    }

    private void LoadInformation(){
        loadOne(R.id.Account,"Account");
    }


    private void loadOne(int id,String content){
        TextView tv = (TextView)findViewById(id);

        String val  = currentUser.getFieldContent(content);
        if(val !=null){
            tv.setText(val);
        }else{
            Utils.debugMessage(this, "找不到属性" + content);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__account, menu);
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

    private String oldPassword;
    private String newPassWord;

    public void submit_Click(View v) {
           if(true == input() ){
               submit();
           }
    }

    private void submit() {
        MyMessage msg = ToolClass.updateuserpassword(currentUser.getUserID(),oldPassword,newPassWord);
        if(msg!=null){
            Utils.showMessage(this, msg.getMess());
        }
    }

    private boolean input() {
        EditText tv = (EditText) findViewById(R.id.Old);
        oldPassword = tv.getText().toString();
        if (PatternValid.validPassword(oldPassword).equals("OK")) {
            tv = (EditText) findViewById(R.id.New);
            newPassWord = tv.getText().toString();
            if (PatternValid.validPassword(newPassWord).equals("OK")) {
                tv = (EditText) findViewById(R.id.New2);
                String tmp = tv.getText().toString();
                if (tmp.equals(newPassWord)) {
                    return true;
                } else {
                    Utils.showMessage(this, "两次输入不一致");
                }

            } else {
                Utils.showMessage(this, "密码格式有问题");
            }
        } else {
            Utils.showMessage(this, "密码格式有问题");
        }
        return  false;
    }

    public void back_Click(View v) {
        Utils.backPage(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Utils.backPage(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
