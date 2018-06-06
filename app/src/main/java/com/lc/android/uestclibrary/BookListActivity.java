package com.lc.android.uestclibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView bookListRecycler;
    private BookListAdapter mAdapter;

    //存储收藏书籍的Sharedperference
    private SharedPreferences mSharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);

        ArrayList<Book> bookList = SingletonBook.getInstance().getData();

        bookListRecycler = (RecyclerView)findViewById(R.id.book_list);
        bookListRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BookListAdapter(bookList);
        bookListRecycler.setAdapter(mAdapter);

        mSharedPreferences = getSharedPreferences("book_collectionInfo", Context.MODE_APPEND);

        mAdapter.setOnItemClickListener(new onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, String tag) {
                AsyncBookInfo asyncTask = new AsyncBookInfo(BookListActivity.this);
                asyncTask.execute(tag);
            }
        });

    }

    private class BookListViewHolder extends RecyclerView.ViewHolder{
        private TextView book_number;
        private TextView book_name;
        private TextView book_publishing_house;
        private Button book_colletion_button;

        public BookListViewHolder(View itemView){
            super(itemView);

            book_number = (TextView)itemView.findViewById(R.id.book_number);
            book_name = (TextView)itemView.findViewById(R.id.book_name);
            book_publishing_house = (TextView)itemView.findViewById(R.id.book_publishing_house);
            //收藏按钮
            book_colletion_button = (Button) itemView.findViewById(R.id.book_collectionButton);
        }

    }

    private class BookListAdapter extends RecyclerView.Adapter<BookListViewHolder> {

        private ArrayList<Book> bookList = new ArrayList<>();
        private onRecyclerViewItemClickListener itemClickListener = null;


        public BookListAdapter(ArrayList<Book> bookList){
            this.bookList = bookList;
        }


        @Override
        public BookListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(Utils.isFastDoubleClick())
                        return;
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(v,(String)v.getTag());
                    }
                }
            });
            return new BookListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BookListViewHolder holder, final int position) {
            holder.book_number.setText(String.valueOf(position+1)+".");
            holder.book_name.setText(bookList.get(position).name);
            holder.book_publishing_house.setText(bookList.get(position).publishing_house);
            holder.itemView.setTag(bookList.get(position).link+"!!!"+bookList.get(position).name);
            holder.book_colletion_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BookListActivity.this,"收藏成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                    String BOOK_NAME;
                    String BOOK_LINK;
                    for(int i = 0; i < 20 ;i++ )
                    {
                        BOOK_NAME = "BOOK_NAME" + String.valueOf(i);
                        BOOK_LINK = "BOOK_LINK" + String.valueOf(i);
                        String temp = mSharedPreferences.getString(BOOK_NAME,null);
                        if (temp == null)
                        {
                            mEditor.putString(BOOK_NAME, bookList.get(position).name);
                            mEditor.putString(BOOK_LINK, bookList.get(position).link);
                            mEditor.commit();
                            return;
                        };
                    };

                }
            });

        }

        @Override
        public int getItemCount() {
            return bookList.size();

        }

        public void setOnItemClickListener(onRecyclerViewItemClickListener listener) {
            this.itemClickListener = listener;
            //  Log.d("ddd", itemClickListener.toString());
        }
    }


    public interface  onRecyclerViewItemClickListener{
        void onItemClick(View v, String tag);
    }

}
