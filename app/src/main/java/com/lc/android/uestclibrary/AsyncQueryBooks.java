package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Lc on 2017/4/3.
 */

public class AsyncQueryBooks extends AsyncTask<String, Integer, String>{

    private String strBookName = "";
    public SearchActivity searchActivity = null;
    private NetOperator.DownloadHtmlResult dhr;
    public ArrayList<Book> book_list = new ArrayList<>();
    public ArrayList<Book_location> book_location_list = new ArrayList<>();
    private boolean which_web;


    public AsyncQueryBooks(SearchActivity searchActivity){
        this.searchActivity = searchActivity;
    }

    @Override
    protected String doInBackground(String... params) {

        if(strBookName.length() >= 1){
            String strSearch ;
            if(params[0] == null)
                strSearch = "X";
            else
                strSearch = params[0];
            try {
                String MIME_strBookName = URLEncoder.encode(strBookName, "utf-8");
                String urlString = LinkIndex.WEB_query + "/search~S1*chx/?searchtype="+ strSearch +"&searcharg=" +
                        MIME_strBookName +
                        "&searchscope=1&sortdropdown=t&SORT=D&extended=0&searchlimits=&searchorigarg=t%7Bu96F7%7D%7Bu96E8%7D";
                dhr = NetOperator.downloadHtml_GET(urlString, "utf8", null);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.i("Search_Activity", "输入书名的URL编码错误");
                return null;
            }

            if (dhr.Success) {
                which_web = NetOperator.Check_Web(dhr.Html);
                if (which_web) {
                    book_location_list = NetOperator.Extract_BookLocation(dhr.Html);
                    if (book_location_list.size() > 0) {
                        Singletonbooklocation.getInstance().setData(book_location_list);
                        Intent intent = new Intent(searchActivity, BookLocationActivity.class);
                        searchActivity.startActivity(intent);
                        return null;
                    } else
                        return "没有详细信息";
                } else {
                    book_list = NetOperator.Extract_BookList(dhr.Html);
                    if (book_list.size() > 0) {
                        SingletonBook.getInstance().setData(book_list);
                        Intent intent = new Intent(searchActivity, BookListActivity.class);
                        searchActivity.startActivity(intent);
                        return null;
                    } else {
                        return "抱歉，搜索不到该书籍";
                    }
                }
            } else {
                return "无法访问网站！";
            }
        }
        else {
            return "请输入您想要查询的书名";
        }
    }

    @Override
    protected void onPostExecute(String state) {
        if(state != null)
            Toast.makeText(searchActivity,state,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        strBookName = searchActivity.editTextBookName.getText().toString();
        String tip = "查询中，请稍等";
        Toast.makeText(searchActivity,tip,Toast.LENGTH_SHORT).show();

    }
}
