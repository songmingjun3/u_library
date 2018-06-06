package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/5/4.
 */

public class AsyncLogin extends AsyncTask<String, Integer, String> {

    LoginActivity loginActivity = null;
    NetOperator.DownloadHtmlResult dhr;
    private String paraUrl;
    ArrayList<String> personalStr = new ArrayList<>();
    private  String strName ;
    private  String strPassword ;

    public AsyncLogin(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.strName = loginActivity.username;
        this.strPassword = loginActivity.password;
    }
    @Override
    protected String doInBackground(String... params) {
        paraUrl = "extpatid=" + strName + "&extpatpw=" + strPassword;
        try {
            dhr = NetOperator.downloadHtml_POST(LinkIndex.WEB_login, paraUrl);
        }
        catch (Exception e){
                return "用户名或密码错误，请重新输入";
        }
        if(dhr.Success){
            personalStr = NetOperator.Extract_Personal(dhr.Html);
            Intent intent = new Intent(loginActivity,SearchActivity.class);
            intent.putExtra("personalStr",personalStr);
            loginActivity.startActivity(intent);
            loginActivity.finish();
            return "登录成功";
        }
        else
            return "无法访问网站";
    }

    @Override
    protected void onPostExecute(String state) {
        if(state != null)
            Toast.makeText(loginActivity,state,Toast.LENGTH_SHORT).show();
    }


}
