package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class Page_Album extends BasePage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_album);

        String[] types = {"活动内分享","站内分享","完全分享"};
        Utils.createActivityTypeSpinner(this, R.id.level, types);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__album, menu);
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

    //添加照片
    public void add_Click(View v){
        this.showPictureSelect();
    }

    //上传照片
    public void submit_Click(View v){
        String picture = Current.getCurrentPicture();
        if(!picture.equals("")){
            String level = String.valueOf(Utils.getSpinnerById(this, R.id.level));
            String title = Utils.getValueOfEditText(this, R.id.title);
            String describe = Utils.getValueOfEditText(this,R.id.describe);
            String ret = ToolClass.addPhoto(Current.getCurrentUser().getUserID(), Current.getCurrentActivity().getActivityID(), picture, title, describe, level);
            ToolClass.uploadFile(picture);
            if(ret!=null && ret.equals("ok")){
                Utils.showMessage(this,"添加照片完成");
                Cache.updateAllPhotos(Current.getCurrentActivity().getActivityID());
                Utils.backPage(this);
            }else{
                Utils.showMessage(this,"添加照片失败");
            }
        }
    }

}
