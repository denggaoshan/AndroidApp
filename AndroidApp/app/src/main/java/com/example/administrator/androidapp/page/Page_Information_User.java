package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.MyMessage;
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
        int[] ids={R.id.NickName,R.id.Sex,R.id.Age,R.id.Constellation,R.id.Profession,R.id.LivePlace,
                R.id.Description,R.id.Phone, R.id.Mailbox, R.id.good,
        };
        String[] attribute={"NickName","Sex","Age","Constellation","Profession","LivePlace","Description","Phone","Mailbox","good"};
        Utils.loadUserInformation(this,currentUser,ids,attribute);
    }

    //点击提交个人信息
    public void submit_Click(View v) {
        try {
            getInput();

            MyMessage msg = ToolClass.updateuserbaseinfo(User.getCurrentUser().getUserID(),nickName,sex, age, constellation,
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
