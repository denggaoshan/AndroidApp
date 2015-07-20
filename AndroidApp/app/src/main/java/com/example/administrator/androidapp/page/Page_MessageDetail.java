package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Inform;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;

public class Page_MessageDetail extends BasePage {

    User dest,source;
    Inform currentInform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_message_detail);

        loadMessageDetail();
    }

    private void loadMessageDetail() {
        dest = User.getOtherUser();
        source = User.getCurrentUser();
        currentInform = Inform.getCurrentInform();

        if(currentInform!=null){
            Utils.setTextView(this,R.id.title_source,currentInform.getTitle());
            Utils.setTextView(this,R.id.content_source,currentInform.getContent());
            Utils.setTextView(this,R.id.time_source,currentInform.getTime());
        }else{
            Utils.debugMessage(this,"currentInform为空");
        }

        Cache.loadImg(this,currentInform.getUserSource().getAvatar(),R.id.image);

        final EditText tx = (EditText)findViewById(R.id.title);

        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tx.getText().toString().equals("标题")){
                    tx.setText("");
                }
            }
        });

        final EditText tx2 = (EditText)findViewById(R.id.content);

        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tx2.getText().toString().equals("标题")) {
                    tx2.setText("");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__message_detail, menu);
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

    public void submit_Click(View v){
        String title = Utils.getValueOfEditText(this, R.id.title);
        String content = Utils.getValueOfEditText(this, R.id.content);
        if(title!=null && content!=null){
            if(content.replace(" ","").equals("") || title.replace(" ","").equals("")){
                Utils.showMessage(this,"标题或者内容不能为空");
            }else{
                String ret = ToolClass.sendPrivateMess(source.getUserID(), dest.getUserID(), title, content);
                if(ret!=null){
                    Utils.showMessage(this,ret);
                    Utils.backPage(this);
                }else{
                    Utils.debugMessage(this,"0002 ret为空");
                }
            }
        }
    }
}
