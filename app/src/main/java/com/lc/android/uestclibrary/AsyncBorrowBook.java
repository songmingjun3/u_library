package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/6/27.
 */

public class AsyncBorrowBook extends AsyncTask<String, Integer, String> {

    NetOperator.DownloadHtmlResult dhr;
    ArrayList<BorrowBook> borrow_Book_list = new ArrayList<>();
    SearchActivity searchActivity = null;

    public AsyncBorrowBook(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = LinkIndex.WEBs_query+"/patroninfo~S1*chx" + "/"+LinkIndex.person_code + "/items";
        dhr = NetOperator.downloadHtml_GET(url,"utf8",LinkIndex.person_cookie);

        if(dhr.Success){
            borrow_Book_list = NetOperator.Extract_BorrowBook(dhr.Html);
            if(borrow_Book_list.size() > 0){
                SingletonBorrowBook.getInstance().setData(borrow_Book_list);
                Intent intent = new Intent(searchActivity,BorrowActivity.class);
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
        super.onPreExecute();
    }
}
