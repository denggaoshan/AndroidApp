package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Page_Organize extends ActionBarActivity {

    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private String type;
    private String place;

    private String[] types = {"户外","运动","玩乐","旅行","音乐","其他"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_organize);

        createActivityTypeSpinner();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_page__organize, menu);
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

    private void createActivityTypeSpinner(){
        //第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
        for(String item : types){
            list.add(item);
        }

        Spinner type = (Spinner)findViewById(R.id.type);
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        type.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
            }
        });

        type.setOnTouchListener(new Spinner.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        type.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
    }

    public void exit_Click(View v){
        Utils.transPage(this, Page_TotalActivity.class);
    }

    public void sure_Click(View v){
        Utils.showMessage(this, getInput());
        if (!getInput().equals("提交中"))
            return;
        commit();
    }

    private void commit(){
        Message msg = Message.getCurrentMessage();

        Message reflect =  ToolClass.launchActivity(msg.getUser().getUserID(), title, content, startTime, endTime, place, type);
        if(checkMess(reflect.getMess())){
            Utils.showMessage(this,"添加活动成功");
            Utils.transPage(this, Page_TotalActivity.class);
        }else{
            Utils.showMessage(this, "添加活动失败");
        }
    }

    private boolean checkMess(String mess)
    {
        if (mess == null || mess.equals("error") || mess.equals(""))
            return false;
        else
            return true;
    }

    //还没验证
    private String getInput() {
        title = Utils.getEditTextById(this,R.id.title);
        if(title.equals("")){
            return "请输入活动标题";
        }

        content = Utils.getEditTextById(this,R.id.content);
        if(content.equals("")){
            return "请输入活动内容";
        }

        startTime = Utils.getEditTextById(this,R.id.startTime);
        if(startTime.equals("")){
            return "请输入活动开始时间";
        }
        endTime = Utils.getEditTextById(this, R.id.endTime);
        if (endTime.equals("")){
            return "请输入活动结束时间";
        }
        place = Utils.getEditTextById(this,R.id.place);
        if (place.equals("")){
            return "请输入地点";
        }
        type = "" + Utils.getSpinnerById(this, R.id.type);

        return "提交中";
    }


}
