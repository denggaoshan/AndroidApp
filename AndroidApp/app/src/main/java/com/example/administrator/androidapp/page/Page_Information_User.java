package com.example.administrator.androidapp.page;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;


public class Page_Information_User extends BasePage {

    User currentUser;

    String nickName,sex,age,constellation,profession,liveplace,description,phone,mailBox;

    private void getInput() {

        if( Utils.isSelectedRadioButton(this, R.id.girl)){
            sex = "1";
        }else{
            sex = "0";
        }

        nickName = getInfoFromViewById(R.id.NickName);
        age = getInfoFromViewById(R.id.Age);
        profession = getInfoFromViewById(R.id.Profession);
        liveplace = getInfoFromViewById(R.id.LivePlace);
        constellation = getInfoFromViewById(R.id.Constellation);
        phone = getInfoFromViewById(R.id.Phone );
        mailBox = Current.getUser().getMailbox();
        description = getInfoFromViewById(R.id.Description);
    }

    private void LoadInformation(){
        int[] ids={R.id.NickName,R.id.Age,R.id.Constellation,R.id.Profession,R.id.LivePlace,
                R.id.Description,R.id.Phone, R.id.good,
        };
        String[] attribute={"NickName","Age","Constellation","Profession","LivePlace","Description","Phone","Good"};
        Utils.loadUserInformation(this, currentUser, ids, attribute);

        if(currentUser.getSex().equals("0")){
            Utils.setSelect(this,R.id.boy,true);
            Utils.setSelect(this,R.id.girl,false);
        }else {
            Utils.setSelect(this,R.id.boy,false);
            Utils.setSelect(this,R.id.girl,true);
        }

        Cache.loadImg(this,currentUser.getAvatar(),R.id.image);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_information);
        currentUser = Current.getUser();
        LoadInformation();
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



    //点击提交个人信息
    public void submit_Click(View v) {
        try {
            getInput();

            MyMessage msg = ToolClass.updateuserbaseinfo(Current.getUser().getUserID(),nickName,sex, age, constellation,
                    profession, liveplace, description, phone);
            if(msg!=null){
                Utils.showMessage(this,msg.getMess());
                User user = msg.getUser();
                if(user!=null){
                    currentUser = user;
                    Current.setUser(currentUser);
                    Utils.backPage(this);
                }
            }
        }catch (Exception e){
            Utils.debugMessage(this,"GetInput有问题");
        }
    }


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



}
