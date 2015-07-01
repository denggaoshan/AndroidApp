package com.example.administrator.androidapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        ListView vi=(ListView) findViewById(R.id.content);
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.activity, new String[] { "title",  "time","position","attending" }, new int[] { R.id.title, R.id.time,R.id.position,R.id.attending});
        vi.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "1234");
        map.put("time", "12:33");
        map.put("position", "12:33");
        map.put("attending", "qqqqqq");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "1234");
        map.put("time", "12:33");
        map.put("position", "12:33");
        map.put("attending", "zzzzzz");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "1234");
        map.put("time", "12:1fasd");
        map.put("position", "11234123");
        map.put("attending", "tttttt");
        list.add(map);

        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_total, menu);
        return true;
    }

    public void edit_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(TotalActivity.this, UserManagerActivity.class);
        TotalActivity.this.startActivity(intent);
        TotalActivity.this.finish();
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
}
