package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/6/27.
 */

public class AsyncReadingHistory extends AsyncTask<String, Integer, String> {

    public NetOperator.DownloadHtmlResult dhr;
    public ArrayList<HistoryBook> history_book_list = new ArrayList<>();
    public SearchActivity searchActivity = null;

    public AsyncReadingHistory(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = LinkIndex.WEBs_query+"/patroninfo~S1*chx" + "/"+LinkIndex.person_code + "/readinghistory";
        dhr = NetOperator.downloadHtml_GET(url,"utf8",LinkIndex.person_cookie);

        if(dhr.Success){
            history_book_list = NetOperator.Extract_ReadingHistory(dhr.Html);
            if(history_book_list.size() > 0){
                SingletonReadingHistory.getInstance().setData(history_book_list);
                Intent intent = new Intent(searchActivity,HistoryActivity.class);
                searchActivity.startActivity(intent);
                return null;
            }
            else
                return "您没有借阅书籍";
        }
        else
            return "无法访问网站";

    }

    @Override
    protected void onPostExecute(String state) {
        if(state != null)
            Toast.makeText(searchActivity,state,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {

        Toast.makeText(searchActivity,"查询中，请稍后",Toast.LENGTH_SHORT).show();
    }
}
