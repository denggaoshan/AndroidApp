package com.example.administrator.androidapp.page;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.tool.Utils;

public class Page_Account extends BasePage {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_account);

        loadUserInformation();
    }

    private void loadUserInformation() {
        currentUser = Current.getUser();
        LoadInformation();
    }

    private void LoadInformation(){
        loadOne(R.id.Account,"Account");
        Cache.loadImg(this,currentUser.getAvatar(),R.id.image);
    }


    private void loadOne(int id,String content){
        String val  = currentUser.getFieldContent(content);
        if(val !=null){
            Utils.setTextView(this,id,val);
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
            if(msg.getMess().equals("ok")){
                Utils.showMessage(this,"修改成功！");
                Utils.backPage(this);
            }else{
                Utils.showMessage(this, "失败了");
            }
        }
    }

    private boolean input() {
        oldPassword = Utils.getValueOfEditText(this, R.id.Old);
        if (PatternValid.validPassword(oldPassword).equals("OK")) {
            newPassWord = Utils.getValueOfEditText(this, R.id.New);
            if (PatternValid.validPassword(newPassWord).equals("OK")) {
                String tmp = Utils.getValueOfEditText(this, R.id.New2);
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

}
