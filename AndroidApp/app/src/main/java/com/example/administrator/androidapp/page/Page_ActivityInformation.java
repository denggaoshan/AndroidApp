package com.example.administrator.androidapp.page;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.*;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page_ActivityInformation extends ActionBarActivity {

    private int currentSelect = 0 ; //当前所选择的标签 0详情 1成员 2相册 3评论
    private Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity_information);

        loadCurrentActivity();

        loadActivityDetail();

    }

    private void loadCurrentActivity() {
        currentActivity = Activity.getCurrentActivity();
    }

    //装载活动详情
    private void loadActivityDetail(){

        ListView vi=(ListView) findViewById(R.id.content);

        SimpleAdapter adapter = new SimpleAdapter(this, getDetailData(), R.layout.content_activity_detail,
                new String[] { "title",  "time","position","attending","image","description"},
                new int[] { R.id.title, R.id.time,R.id.position,R.id.attending,R.id.image,R.id.description});

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__activity_information, menu);
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

    private List<Map<String, Object>> getDetailData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("title", currentActivity.getTitle());
        ret.put("time", currentActivity.getStartTime());
        ret.put("position", currentActivity.getPlace());
        ret.put("attending", currentActivity.getUserCount());
        ret.put("description", currentActivity.getContent());
        list.add(ret);
        return list;
    }

    private  Map<String, Object> getOneMember(User user){
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("name", user.getNickName());
        ret.put("age", user.getAge());
        ret.put("time", "07月02日 10:00");
        return ret;
    }

    private List<? extends Map<String, ?>> getMemberData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        ActivityInfo info = ToolClass.getActivityInfo(User.getCurrentUser().getUserID(), currentActivity.getActivityID());

        //找出参加活动的所有用户

        return list;
    }

    private void changeFocus(int i){
        int[] ids = {R.id.details,R.id.member,R.id.album,R.id.comment};

        for(int id:ids){
            LinearLayout layout=(LinearLayout) findViewById(id);
            layout.setBackgroundColor(Color.WHITE);
        }

        LinearLayout rn=(LinearLayout) findViewById(ids[i]);
        rn.setBackgroundColor(0xFF50d2c2);
    }

    public void album_Click(View v) {
        changeFocus(2);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
    }

    public void comment_Click(View v) {
        changeFocus(3);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
    }

    public void details_Click(View v) {
        changeFocus(0);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();

        loadActivityDetail();
    }


    public void member_Click(View v) {
        changeFocus(1);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();

        SimpleAdapter adapter = new SimpleAdapter(this, getMemberData(), R.layout.content_activity_member,
                new String[] { "name",  "age","time"},
                new int[] { R.id.name, R.id.age,R.id.time});

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if ((view instanceof ImageView) & (data instanceof Bitmap)) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }
                return false;
            }
        });
        vi.setAdapter(adapter);
    }



    public void close_Click(View v) {
        Utils.transPage(this, Page_TotalActivity.class);
    }



}
