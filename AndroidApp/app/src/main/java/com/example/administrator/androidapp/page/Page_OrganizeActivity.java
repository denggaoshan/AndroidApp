package com.example.administrator.androidapp.page;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.DateFactory;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Page_OrganizeActivity extends BasePage {

    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private static String title;
    private static String content;
    private static String startTime;
    private static String endTime;
    private static String type;
    private static String place;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_organize);

        //多选框
        String[] types = {"户外","运动","玩乐","旅行","音乐","其他"};
        Utils.createActivityTypeSpinner(this,R.id.type,types);
        loadInformation();
        initCurTime();
    }

    private void loadInformation() {
        int[] ids = {R.id.title,R.id.content,R.id.place};
        String[] vals = {title,content,Page_Map.getResultAdress()};
        for(int i=0;i<ids.length;i++){
            Utils.setEditView(this,ids[i],vals[i]);
        }
    }


    private void initCurTime(){
        String curTime = "";
        Calendar cal = Calendar.getInstance();
        curTime += cal.get(Calendar.YEAR);
        curTime += "-";
        curTime += (cal.get(Calendar.MONTH) + 1);
        curTime += "-";
        curTime += cal.get(Calendar.DAY_OF_MONTH);
        ((Button)findViewById(R.id.startTime)).setText(curTime);
        ((Button)findViewById(R.id.endTime)).setText(curTime);
        String curHour = "";
        curHour += cal.get(Calendar.HOUR_OF_DAY);
        curHour += " : ";
        curHour += cal.get(Calendar.MINUTE);
        ((Button)findViewById(R.id.startHour)).setText(curHour);
        ((Button)findViewById(R.id.endHour)).setText(curHour);
    }

    public void setStartTime(View v){
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(Page_OrganizeActivity.this,
                new DatePickerDialog.OnDateSetListener()
                {
                    public void onDateSet(DatePicker dp, int year,
                                          int month, int dayOfMouth)
                    {
                        Button btn = (Button)findViewById(R.id.startTime);
                        btn.setText(year + "-" + (month + 1) + "-" + dayOfMouth);
                    }
                }
                , c.get(Calendar.YEAR)
                , c.get(Calendar.MONTH)
                , c.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void setEndTime(View v){
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(Page_OrganizeActivity.this,
                new DatePickerDialog.OnDateSetListener()
                {
                    public void onDateSet(DatePicker dp, int year,
                                          int month, int dayOfMouth)
                    {
                        Button btn = (Button)findViewById(R.id.endTime);
                        btn.setText(year + "-" + (month + 1) + "-" + dayOfMouth);
                    }
                }
                , c.get(Calendar.YEAR)
                , c.get(Calendar.MONTH)
                , c.get(Calendar.DAY_OF_MONTH)).show();
    }
    public void setStartHour(View v){
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(Page_OrganizeActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        // TODO Auto-generated method stub
                        Button btn = (Button)findViewById(R.id.startHour);
                        btn.setText(hour + ":" + minute);
                    }
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE), true).show();
    }
    public void setEndHour(View v){
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(Page_OrganizeActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        // TODO Auto-generated method stub
                        Button btn = (Button)findViewById(R.id.endHour);
                        btn.setText(hour + ":" + minute);
                    }
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE), true).show();
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



    public void sure_Click(View v){
        Utils.showMessage(this, getInput());
        if (!getInput().equals("提交中"))
            return;
        commit();
    }

    private void commit(){
        MyMessage msg = Current.getCurrentMyMessage();

        MyMessage reflect =  ToolClass.launchActivity(msg.getUser().getUserID(), title, content, startTime, endTime, place, type);
        if(checkMess(reflect.getMess())){
            Utils.showMessage(this,"添加活动成功");
            Utils.transPage(this, Page_TotalActivity.class);
        }else{
            Utils.showMessage(this, "添加活动失败"+reflect.getMess());
        }
    }

    private boolean checkMess(String mess)
    {
        if(mess.equals("ok")){
            return true;
        }
        return false;
    }

    //获得输入
    private String getInput() {
        title = Utils.getValueOfEditText(this, R.id.title);
        if(title.equals("")){
            return "请输入活动标题";
        }
        content = Utils.getValueOfEditText(this, R.id.content);
        if(content.equals("")){
            return "请输入活动内容";
        }
        startTime = Utils.getTimeBox(this,R.id.startTime,R.id.endTime);
        endTime = Utils.getTimeBox(this,R.id.endTime,R.id.endHour);
        place = Utils.getValueOfEditText(this, R.id.place);
        if (place.equals("")){
            return "请输入地点";
        }
        type = "" + Utils.getSpinnerById(this, R.id.type);
        return "提交中";
    }

    public void position_Click(View v){
        getInput();//保存输入内容
        Utils.transPage(this,Page_Map.class);
    }

}
