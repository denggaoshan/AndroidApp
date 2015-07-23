package com.example.administrator.androidapp.msg;

import android.widget.Button;

import com.example.administrator.androidapp.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/7/15.
 */
public class DateFactory {

    public static Date createDateByString(String date){
        String[] tmp = date.split(" ");
        String[] tmp2 = tmp[0].split("-");
        String[] tmp3 = tmp[1].split(":");

        Date ret = new Date();
        ret.setYear(Integer.parseInt(tmp2[0]) - 1900);
        ret.setMonth(Integer.parseInt(tmp2[1])-1);
        ret.setDate(Integer.parseInt(tmp2[2]));
        ret.setHours(Integer.parseInt(tmp3[0]));
        ret.setMinutes(Integer.parseInt(tmp3[1]));
        if(tmp3.length>3){
            ret.setSeconds(Integer.parseInt(tmp3[2]));
        }else{
            ret.setSeconds(0);
        }
        return ret;
    }

    public static String createStringByDate(Date date){
        String year = String.valueOf(date.getYear() + 1900);

        String month = String.valueOf(date.getMonth()+1);
        if(month.length()==1){
            month = "0"+month;
        }
        String day = String.valueOf(date.getDate());
        if(day.length() == 1){
            day = "0" + day;
        }
        String hour = String.valueOf(date.getHours());
        if(hour.length() == 1){
            hour = "0" + hour;
        }
        String minu = String.valueOf(date.getMinutes());
        if(minu.length() == 1){
            minu = "0" + minu;
        }
        String sec = String.valueOf(date.getSeconds());
        if(sec.equals("0")){
            sec = "00";
        }

        String ret = year+"-"+month+"-"+day+" "+hour+":"+minu+":"+sec;
        return ret;
    }

    //只获得前面的时间
    public static String getFrontDate(String date){
        String dates[] = date.split(" ");
        return dates[0];
    }


    public static Date getCurrentTime(){
        String curTime = "";
        Calendar cal = Calendar.getInstance();
        curTime += cal.get(Calendar.YEAR);
        curTime += "-";
        curTime += (cal.get(Calendar.MONTH) + 1);
        curTime += "-";
        curTime += cal.get(Calendar.DAY_OF_MONTH);
        String curHour = "";
        curHour += cal.get(Calendar.HOUR_OF_DAY);
        curHour += ":";
        curHour += cal.get(Calendar.MINUTE);

        Date now  =createDateByString(curTime+" "+curHour);
        if(now!=null){
            return now;
        }else{
            return null;
        }

    }


}
