package com.example.administrator.androidapp;

import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        Button loadBtn = (Button)findViewById(R.id.loadButton);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.registered);
                EditText account_RegTxt = (EditText)findViewById(R.id.account_RegText);
                //account_RegTxt.setText("sss");
                //WebView webView = (WebView)findViewById(R.id.webView);
                //webView.loadUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=URLconnection%20connect&rsv_pq=dc17b74400014a8f&rsv_t=1f01R9YxbSGroJtpnP%2FUaxLenHl4KoeieexSxaxkf3WZblM8Jyw%2Bt9opbqI&rsv_enter=0&rsv_jmp=slow");
/*                String s = HttpRequest.sendGet("http://www.tuling123.com/openapi/api", "key=3235efe35b29467f2dffebd09b510a20&info=ÄãºÃ");
                System.out.println(s);
                account_RegTxt.setText("asdad" + s);*/
                try
                {
                    String urlNameString = "http://www.tuling123.com/openapi/api?key=3235efe35b29467f2dffebd09b510a20&info=ÄãºÃ";
                    URL realUrl = new URL(urlNameString);
                    URLConnection connection = realUrl.openConnection();
                    connection.connect();
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            connection.getInputStream()));
                    String result = "";
                    String line;
                    try
                    {
                        while ((line = in.readLine()) != null) {
                            result += line;
                        }
                    }
                    catch(Exception ee)
                    {
                        EditText tell_RegText = (EditText)findViewById(R.id.tell_RegTxt);
                        tell_RegText.setText("string exception");
                    }
                    finally {
                        EditText account_RegText = (EditText)findViewById(R.id.account_RegText);
                        account_RegText.setText(result);
                    }

                }
                catch (Exception e)
                {
                    EditText tell_RegText = (EditText)findViewById(R.id.tell_RegTxt);
                    tell_RegText.setText("exception" + "00" + e.getMessage() + "00" + e.getClass());
                }
            }
        });

        Button registerBtn = (Button)findViewById(R.id.registerButton);
/*        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });*/
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
