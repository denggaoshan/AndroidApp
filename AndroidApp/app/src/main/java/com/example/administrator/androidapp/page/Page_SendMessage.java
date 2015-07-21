package com.example.administrator.androidapp.page;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;

public class Page_SendMessage extends BasePage {


    User source,dest;

    private static String sendMessageType; //发消息的类型 1为给别人 2为群发

    public static void setMessageType(String type){
        sendMessageType = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_send_message);

        if(sendMessageType.equals("1")){
            //给某人发
            source = Current.getCurrentUser();
            dest = Current.getOtherUser();

            if(source==null || dest==null){
                Utils.debugMessage(this,"0001 source 或者 dest 为空");
            }
        }else{
            //群发消息
            source = Current.getCurrentUser();
        }



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
                if (tx2.getText().toString().equals("内容")) {
                    tx2.setText("");
                }
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__send_message, menu);
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
                Utils.backPage(this);
            }else{
                String ret = null;
                //发消息
                if(sendMessageType.equals("1")){
                    ret = ToolClass.sendPrivateMess(source.getUserID(),dest.getUserID(),title,content);
                }else{
                    ret =ToolClass.fsendMess(source.getUserID(),Current.getCurrentActivity().getActivityID(),title,content);
                }
                //结果
                if(ret!=null){

                    if(ret.equals("ok")){
                        Utils.showMessage(this, "已发送");
                        Cache.updateAllMessages(source.getUserID());
                        Utils.backPage(this);
                    }else{
                        Utils.showMessage(this, "发送失败");
                    }

                }else{
                    Utils.debugMessage(this, "0002 ret为空");
                }
            }
        }
    }
}
