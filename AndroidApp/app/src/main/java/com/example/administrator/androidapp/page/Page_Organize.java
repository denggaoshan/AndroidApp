package com.example.administrator.androidapp.page;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import com.example.administrator.androidapp.msg.DateFactory;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        ((EditText)findViewById(R.id.place)).setText(ToolClass.getCurLocation());
        initCurTime();
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
        new DatePickerDialog(Page_Organize.this,
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
        new DatePickerDialog(Page_Organize.this,
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
        new TimePickerDialog(Page_Organize.this,
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
        new TimePickerDialog(Page_Organize.this,
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

    //获得输入
    private String getInput() {
        title = Utils.getEditTextById(this,R.id.title);
        if(title.equals("")){
            return "请输入活动标题";
        }

        content = Utils.getEditTextById(this,R.id.content);
        if(content.equals("")){
            return "请输入活动内容";
        }

        Button btn1 = (Button)findViewById(R.id.startTime);
        Button btn2 = (Button)findViewById(R.id.startHour);
        String btn2_text =  btn2.getText().toString();
        btn2_text = btn2_text.replace(" ","");
        startTime = btn1.getText().toString()+" " + btn2_text;

        Date tmp = DateFactory.createDateByString(startTime);
        startTime = DateFactory.createStringByDate(tmp);

        btn1 = (Button)findViewById(R.id.endTime);
        btn2 = (Button)findViewById(R.id.endHour);
        btn2_text =  btn2.getText().toString();
        btn2_text = btn2_text.replace(" ", "");
        endTime = btn1.getText().toString()+" " + btn2_text;
        tmp = DateFactory.createDateByString(endTime);
        endTime = DateFactory.createStringByDate(tmp);

        place = Utils.getEditTextById(this,R.id.place);
        if (place.equals("")){
            return "请输入地点";
        }

        type = "" + Utils.getSpinnerById(this, R.id.type);

        return "提交中";
    }


}
