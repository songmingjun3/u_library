package com.lc.android.uestclibrary;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Lc on 2017/4/16.
 */

public class LinkIndex {
    public static final String WEB_query = "http://webpac.uestc.edu.cn";
    public static final String WEBs_query = "https://webpac.uestc.edu.cn";
    public static final String WEB_book_location = "http://10.21.16.217/RFIDWeb/TSDW/GotoFlash.aspx?szBarCode=";
    public static final String WEB_login = "https://webpac.uestc.edu.cn/patroninfo*chx";
    public static final String WEB_QA = "http://www.lib.uestc.edu.cn/message-board?page=1&search=";

    public static String person_code ;
    public static String person_cookie;
//    public  String login_state = null;


    public static void hideKeyboard(MotionEvent event, View view, Activity activity){
        try{
            if(view != null && view instanceof EditText){
                int[] location = {0,0};
                view.getLocationInWindow(location);
                int left = location[0];
                int top  = location[1];
                int right = left + view.getWidth();
                int bottom = top + view.getHeight();
                if(event.getRawX() < left || event.getRawX() > right || event.getRawY() < top || event.getRawY() > bottom){
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

class Utils{
    private static long lastFastTime;
    public static boolean isFastDoubleClick(){
        long time = System.currentTimeMillis();
        if(time - lastFastTime < 1000)
            return true;

        lastFastTime = time;
        return false;
    }
}
