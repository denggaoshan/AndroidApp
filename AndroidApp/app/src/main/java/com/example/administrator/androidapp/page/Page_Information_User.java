package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;


public class Page_Information_User extends ActionBarActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_information);
        currentUser = User.getCurrentUser();
        LoadInformation();
        try {
            ImageView iv = (ImageView)findViewById(R.id.imageView13);
            iv.setImageBitmap(ToolClass.resizeBitmap(Cache.getUserAvater(), this, iv.getWidth(), iv.getHeight()));
        }catch (Exception e){
            Utils.debugMessage(this,"个人页面头像有BUG啊");
        }
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

    private void LoadInformation(){
        loadOne(R.id.NickName,"NickName");
        loadOne(R.id.Sex,"Sex");
        loadOne(R.id.Age,"Age");
        loadOne(R.id.Constellation, "Constellation");
        loadOne(R.id.Profession, "Profession");
        loadOne(R.id.LivePlace,"LivePlace");
        loadOne(R.id.Description, "Description");
        loadOne(R.id.Phone, "Phone");
        loadOne(R.id.Mailbox, "Mailbox");
    }


    private void loadOne(int id,String content){
        TextView tv = (TextView)findViewById(id);

        String val  = currentUser.getFieldContent(content);
        if(val !=null){
            tv.setText(val);
        }else{
            Utils.debugMessage(this,"找不到属性"+content);
        }
    }

    public void back_Click(View v) {
        Utils.backPage(this);
    }


    //点击提交个人信息
    public void submit_Click(View v) {
        try {
            getInput();

            Message msg = ToolClass.updateuserbaseinfo(User.getCurrentUser().getUserID(),nickName,sex, age, constellation,
                    profession, liveplace, description, phone, mailBox);
            if(msg!=null){
                Utils.showMessage(this,msg.getMess());
                User user = msg.getUser();
                if(user!=null){
                    currentUser = user;
                    User.setCurrentUser(currentUser);
                    Utils.backPage(this);
                }
            }
        }catch (Exception e){
            Utils.debugMessage(this,"GetInput有问题");
        }
    }

    String nickName,sex,age,constellation,profession,liveplace,description,phone,mailBox;

    private String getInfoFromViewById(int id){
        EditText ed = (EditText)findViewById(id);
        String ret ="";

        if(ed!=null){
            ret = ed.getText().toString();
            if(id==R.id.Sex)
            {//如果是性别，做转换
                if(ret.equals("男")){
                    ret = "0";
                }else if(ret.equals("女")){
                    ret = "1";
                }else{
                    Utils.debugMessage(this,"性别输入有问题");
                    return "0";
                }
            }
        }
        else
        {
            Utils.debugMessage(this,"找不到视图上的ID");
        }
        return ret;
    }

    private void getInput() {
        sex = getInfoFromViewById(R.id.Sex);
        nickName = getInfoFromViewById(R.id.NickName);
        age = getInfoFromViewById(R.id.Age);
        profession = getInfoFromViewById(R.id.Profession);
        liveplace = getInfoFromViewById(R.id.LivePlace);
        constellation = getInfoFromViewById(R.id.Constellation);
        phone = getInfoFromViewById(R.id.Phone );
        mailBox = getInfoFromViewById(R.id.Mailbox);
        description = getInfoFromViewById(R.id.description);
    }


}
