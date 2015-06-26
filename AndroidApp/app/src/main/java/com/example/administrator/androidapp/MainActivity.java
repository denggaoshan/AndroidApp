package com.example.administrator.androidapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.protocol.HTTP;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadBtn = (Button)findViewById(R.id.loadButton);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.registered);
                EditText account_RegTxt = (EditText)findViewById(R.id.account_RegText);
                //account_RegTxt.setText("sss");
                //WebView webView = (WebView)findViewById(R.id.webView);
                //webView.loadUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=URLconnection%20connect&rsv_pq=dc17b74400014a8f&rsv_t=1f01R9YxbSGroJtpnP%2FUaxLenHl4KoeieexSxaxkf3WZblM8Jyw%2Bt9opbqI&rsv_enter=0&rsv_jmp=slow");
                String s = HttpRequest.sendGet("http://www.tuling123.com/openapi/api", "key=3235efe35b29467f2dffebd09b510a20&info=ÄãºÃ");
                System.out.println(s);
                account_RegTxt.setText("asdad" + s)

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
