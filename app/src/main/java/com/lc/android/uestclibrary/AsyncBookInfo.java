package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import static com.lc.android.uestclibrary.NetOperator.Extract_BookLocation;

/**
 * Created by Lc on 2017/4/16.
 */

public class AsyncBookInfo extends AsyncTask<String, Integer, String> {

    BookListActivity bookListActivity = null;
    NetOperator.DownloadHtmlResult dhr;
    ArrayList<Book_location> book_location_list = new ArrayList<>();

    public AsyncBookInfo(BookListActivity bookListActivity){
        this.bookListActivity = bookListActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String[] tran = params[0].split("!!!");
        String tran_link = tran[0];
        String tran_name = tran[1];
        String urlString = LinkIndex.WEB_query + tran_link;
        dhr = NetOperator.downloadHtml_GET(urlString,"utf8", null);

        if(dhr.Success){
            book_location_list = Extract_BookLocation(dhr.Html);
            if(book_location_list.size() > 0 ){
                Singletonbooklocation.getInstance().setData(book_location_list);
                Intent intent = new Intent(bookListActivity,BookLocationActivity.class);
                intent.putExtra("BookLocationTheme",tran_name);
                bookListActivity.startActivity(intent);
                return null;
            }
            else
                return "没有详细信息";
        } else{
            return "无法访问网站";
        }
    }

    @Override
    protected void onPostExecute(String state) {
        if(state != null)
            Toast.makeText(bookListActivity,state,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
