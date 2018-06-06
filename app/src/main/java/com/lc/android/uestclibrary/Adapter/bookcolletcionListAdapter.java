package com.lc.android.uestclibrary.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lc.android.uestclibrary.BookCollectionInfo;
import com.lc.android.uestclibrary.R;

import java.util.List;

/**
 * Created by 6G on 2017/6/26.
 */

public class bookcolletcionListAdapter extends BaseAdapter {

    public SharedPreferences mSharedPreferences;
    private List<BookCollectionInfo> mBookCollectionInfoList;
    public LayoutInflater inflater;

    public bookcolletcionListAdapter(List<BookCollectionInfo> mBookCollectionInfoList, Context context,SharedPreferences mSharedPreferences)
    {
        this.mBookCollectionInfoList = mBookCollectionInfoList;
        this.inflater = LayoutInflater.from(context);
        this.mSharedPreferences = mSharedPreferences;

    }

    @Override
    public int getCount() {
        return mBookCollectionInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBookCollectionInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView =inflater.inflate(R.layout.book_collectionlistitem,null);
        };

        TextView bookcollection_name = (TextView) convertView.findViewById(R.id.bookcollection_name);
        TextView bookcollection_publishing_house = (TextView) convertView.findViewById(R.id.bookcollection_publishing_house);
        Button DeleteItem = (Button) convertView.findViewById(R.id.DeleteItem);
        DeleteItem.setTag(position);
        bookcollection_name.setText(mBookCollectionInfoList.get(position).getBookName());
        bookcollection_publishing_house.setText("");


        DeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mposition = Integer.parseInt(v.getTag().toString());
                mBookCollectionInfoList.remove(mposition);
                bookcolletcionListAdapter.this.notifyDataSetChanged();
                SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                String BOOK_NAME = "BOOK_NAME" + String.valueOf(mposition);
                mEditor.remove(BOOK_NAME);
                mEditor.commit();
//问题部分
//                for(int i = mposition ;i <20 ;i++)
//                {
//                    BOOK_NAME =  "BOOK_NAME" + String.valueOf(i);
//                    String BOOK_NAME1 = "BOOK_NAME" + String.valueOf(i+1);
//                    String temp = mSharedPreferences.getString(BOOK_NAME1,"");
//                    if(temp != null)
//                    {
//                        mEditor.putString(BOOK_NAME, temp);
//                        mEditor.commit();
//                    }
//                }

            }
        });


        return convertView;
    }
}
