package com.lc.android.uestclibrary;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lc.android.uestclibrary.Adapter.bookcolletcionListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 6G on 2017/6/26.
 */

public class BookCollectionListActivity extends AppCompatActivity {

    public SharedPreferences mSharedPreferences;
    private bookcolletcionListAdapter mAdapter;
    private ListView mListView;
    private List<BookCollectionInfo> mBookCollectionInfoList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_collectionlist);


        mSharedPreferences = getSharedPreferences("book_collectionInfo", 0);
        mListView = (ListView) findViewById(R.id.book_collection_List);

        createAdapter(mBookCollectionInfoList);

        mListView.setAdapter(mAdapter);







    }



    public void createAdapter( List<BookCollectionInfo> mBookCollectionInfoList)
    {
        for(int i = 0; i < 20; i++)
        {
            String BOOK_NAME = "BOOK_NAME" + String.valueOf(i);
            String BOOK_LINK = "BOOK_LINK" + String.valueOf(i);
            String temp_name = mSharedPreferences.getString(BOOK_NAME,null);
            String temp_link = mSharedPreferences.getString(BOOK_LINK,null);
            if(temp_name != null)
            {
                BookCollectionInfo bookCollectionInfo = new BookCollectionInfo();
                bookCollectionInfo.setBookName(temp_name);
                bookCollectionInfo.setLink(temp_link);
                mBookCollectionInfoList.add(bookCollectionInfo);
            }
            else
            {
                continue;
            };


        };
        mAdapter = new bookcolletcionListAdapter(mBookCollectionInfoList,BookCollectionListActivity.this,mSharedPreferences);
    };
}
