package com.lc.android.uestclibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookLocationActivity extends AppCompatActivity {

    private RecyclerView bookRecycler;
    private BookAdapter bAdaper;
    private TextView themeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_location);

        themeTextView = (TextView)findViewById(R.id.book_location_theme);
        Intent intent = getIntent();
        themeTextView.setText(intent.getStringExtra("BookLocationTheme"));
        ArrayList<Book_location> book_location_list = Singletonbooklocation.getInstance().getData();

        bookRecycler = (RecyclerView)findViewById(R.id.book_location_list);
        bookRecycler.setLayoutManager(new LinearLayoutManager(this));
        bAdaper = new BookAdapter(BookLocationActivity.this,book_location_list);
        bookRecycler.setAdapter(bAdaper);
    }

    private class BookViewHolder extends RecyclerView.ViewHolder{
        private TextView book_place;
        //private TextView book_retrieve_number;
        private TextView book_state;
        private TextView book_location;

        public BookViewHolder(View itemView){
            super(itemView);

            book_place = (TextView)itemView.findViewById(R.id.book_place);
            //book_retrieve_number = (TextView)itemView.findViewById(R.id.book_retrieve_number);
            book_state = (TextView)itemView.findViewById(R.id.book_state);
            book_location = (TextView)itemView.findViewById(R.id.book_location);
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

        private LayoutInflater inflater;
        private ArrayList<Book_location> book_location_list = new ArrayList<>();


        public BookAdapter(Context context, ArrayList<Book_location> book_location_list){
            this.book_location_list = book_location_list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BookViewHolder holder = new BookViewHolder(inflater.inflate(R.layout.book_location,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(BookViewHolder holder, int position) {
            holder.book_place.setText(book_location_list.get(position).place);
            //holder.book_retrieve_number.setText(book_location_list.get(position).retrieve_number);
            holder.book_state.setText(book_location_list.get(position).book_state);
            holder.book_location.setText(book_location_list.get(position).location);
        }

        @Override
        public int getItemCount() {
            return book_location_list.size();
        }
    }
}
