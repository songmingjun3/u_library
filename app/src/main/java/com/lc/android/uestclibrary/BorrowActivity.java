package com.lc.android.uestclibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BorrowActivity extends AppCompatActivity {

    private RecyclerView borrowBookRecycler;
    private BorrowBookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        ArrayList<BorrowBook> borrow_book_List = SingletonBorrowBook.getInstance().getData();

        borrowBookRecycler = (RecyclerView)findViewById(R.id.borrow);
        borrowBookRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BorrowBookAdapter(borrow_book_List);
        borrowBookRecycler.setAdapter(mAdapter);

    }

    private class BorrowBookViewHolder extends RecyclerView.ViewHolder{
        private TextView borrow_number;
        private TextView borrow_name;
        private TextView drawback_date;

        public BorrowBookViewHolder(View itemView) {
            super(itemView);

            borrow_number = (TextView)itemView.findViewById(R.id.borrow_book_number);
            borrow_name = (TextView)itemView.findViewById(R.id.borrow_book_name);
            drawback_date = (TextView)itemView.findViewById(R.id.drawback_date);

        }
    }

    private class BorrowBookAdapter extends RecyclerView.Adapter<BorrowBookViewHolder> {

        private ArrayList<BorrowBook> borrow_book_List = new ArrayList<>();

        public BorrowBookAdapter(ArrayList<BorrowBook> borrow_book_List) {
            this.borrow_book_List = borrow_book_List;
        }

        @Override
        public int getItemCount() {
            return borrow_book_List.size();
        }

        @Override
        public void onBindViewHolder(BorrowBookViewHolder holder, int position) {
            holder.borrow_number.setText(String.valueOf(position+1)+".");
            holder.borrow_name.setText(borrow_book_List.get(position).borrowBookName);
            holder.drawback_date.setText(borrow_book_List.get(position).date);
        }

        @Override
        public BorrowBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.borrow_book,parent,false);
            return new BorrowBookViewHolder(view);
        }
    }

}
