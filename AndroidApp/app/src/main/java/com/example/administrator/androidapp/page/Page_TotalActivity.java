package com.example.administrator.androidapp.page;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.MyActivity;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page_TotalActivity extends ActionBarActivity {

    private int curPage = 1;
    private int activityType = 0;
    private int applyAble = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_total_activity);
        loadActivity();
    }

    private void loadActivity(){

        ListView vi=(ListView) findViewById(R.id.content);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.content_total_activity,
                new String[] { "title",  "time","position","attending","image"},
                new int[] { R.id.title, R.id.time,R.id.position,R.id.attending,R.id.image});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if( (view instanceof ImageView) & (data instanceof Bitmap) ) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }
                return false;
            }
        });
        vi.setAdapter(adapter);

        vi.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<activities.length){
                    MyActivity.setCurrentActivity(activities[position]);
                }else{
                    Utils.debugMessage(Page_TotalActivity.this,"活动数组越界");
                }
                Utils.transPage(Page_TotalActivity.this,Page_ActivityInformation.class);
            }
        });

    }

    MyActivity[] activities;

    private List<Map<String, Object>> getData() {
        Message msg = ToolClass.getActivityList("" + curPage, "" + activityType, "" + applyAble);
        activities = msg.getActivities();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (activities != null) {
            for (int i = 0; i < activities.length; i++) {
                list.add(getActivityData(activities[i]));
            }
        }
        return list;
    }

    private  Map<String, Object> getActivityData(MyActivity activity){
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("title", activity.getTitle());
        ret.put("time", activity.getStartTime());
        ret.put("position", activity.getPlace());
        ret.put("attending", activity.getUserCount());
        Bitmap temp = ToolClass.returnBitMap(activity.getAvatar());
        ret.put("image", ToolClass.returnBitMap(activity.getAvatar()));
        return ret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_total, menu);
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

    private PopupWindow popupWindow;

    public void func_Click(View v){
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.menu_total, null,false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 300, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 这里是位置显示方式,在屏幕的左侧
        popupWindow.showAsDropDown(v, 0, 0);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    public void add_Click(View v){
        Utils.transPage(this, Page_Organize.class);
    }

    public void logout_Click(View v){
        Utils.clearLogData();
        Utils.transPage(this,Page_Main.class);
    }

    public void edit_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(Page_TotalActivity.this, Page_UserManager.class);
        Page_TotalActivity.this.startActivity(intent);
        Page_TotalActivity.this.finish();
    }

}
