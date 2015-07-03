package com.example.administrator.androidapp.page;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.core.Activity;
import com.example.administrator.androidapp.core.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page_TotalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_total_activity);

        ListView vi=(ListView) findViewById(R.id.content);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.content_total_activity, new String[] { "title",  "time","position","attending" }, new int[] { R.id.title, R.id.time,R.id.position,R.id.attending});
        vi.setAdapter(adapter);
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

    public void edit_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(Page_TotalActivity.this, Page_UserManager.class);
        Page_TotalActivity.this.startActivity(intent);
        Page_TotalActivity.this.finish();
    }

    private List<Map<String, Object>> getData() {
        User user = User.getCurrentUser();
        Activity[] activities = user.getActivities();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i=0;i<activities.length;i++){
            list.add(getActivityData(activities[i]));
        }
        return list;
    }

    private  Map<String, Object> getActivityData(Activity activity){
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("title", activity.getTitle());
        ret.put("time", activity.getStartTime());
        ret.put("position", activity.getPlace());
        ret.put("attending", activity.getUserCount());
        return ret;
    }
}
