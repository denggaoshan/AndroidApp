package com.example.administrator.androidapp.page;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Comment;
import com.example.administrator.androidapp.msg.MyActivity;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.msg.UserAndExplain;
import com.example.administrator.androidapp.msg.UserAndExplainArray;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page_MemberManager extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_member_manager);
        loadActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__member_manager, menu);
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

    private MyActivity currentActivity;

    //返回上级
    public void back_Click(View v) {
        Utils.transPage(this, Page_ActivityInformation.class);
    }

    //同意请求
    public void agree_Click(View v) {

    }

    //拒绝请求
    public void refuse_Click(View v) {


    }

    private UserAndExplain[] allRequests ; //所有请求
    //装载所有活动
    private void loadActivity(){
        currentActivity  = MyActivity.getCurrentActivity();
        UserAndExplainArray ret = ToolClass.getApplication(User.getCurrentUser().getUserID(), currentActivity.getActivityID());

        allRequests = ret.getUserAndExplains();

        ListView vi=(ListView) findViewById(R.id.requests);
        SimpleAdapter adapter = new SimpleAdapter(this, getDetailData(), R.layout.content_member_request,
                new String[] { "name",  "time","content"},
                new int[] { R.id.name, R.id.time,R.id.content});
        vi.setAdapter(adapter);
    }

    private List<? extends Map<String,?>> getDetailData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(UserAndExplain req:allRequests){
            list.add(getOneRequset(req));
        }

        return list;
    }
    private Map<String, Object> getOneRequset(UserAndExplain req) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("name",req.getUser().getNickName() );
        ret.put("time",req.getTime());
        ret.put("content",req.getExpain());
        return ret;
    }

    ;
}
