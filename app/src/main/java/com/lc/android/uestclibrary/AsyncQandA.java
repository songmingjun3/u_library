package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/6/26.
 */

public class AsyncQandA extends AsyncTask<String, Integer, String> {

    NetOperator.DownloadHtmlResult dhr;
    ArrayList<QA> qa_list = new ArrayList<>();
    SearchActivity searchActivity = null;

    public AsyncQandA(SearchActivity searchActivity){
        this.searchActivity = searchActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = LinkIndex.WEB_QA;
        dhr = NetOperator.downloadHtml_GET(urlString,"utf8", null);

        if(dhr.Success){
            qa_list = NetOperator.Extract_QA(dhr.Html);
            SingletonQA.getInstance().setData(qa_list);
            Intent intent = new Intent(searchActivity,QAActivity.class);
            searchActivity.startActivity(intent);
            return null;
        }else
            return("无法访问网站");

    }

    @Override
    protected void onPostExecute(String state) {
        if(state != null)
            Toast.makeText(searchActivity,state,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
