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

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyBookRecycler;
    private HistoryBookAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        ArrayList<HistoryBook> history_book_List = SingletonReadingHistory.getInstance().getData();

        historyBookRecycler = (RecyclerView)findViewById(R.id.history);
        historyBookRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HistoryBookAdapter(history_book_List);
        historyBookRecycler.setAdapter(mAdapter);


    }

    private class HistoryBookViewHolder extends RecyclerView.ViewHolder{
        private TextView history_number;
        private TextView history_name;
        private TextView history_date;

        public HistoryBookViewHolder(View itemView) {
            super(itemView);

            history_number = (TextView)itemView.findViewById(R.id.history_book_number);
            history_name = (TextView)itemView.findViewById(R.id.history_book_name);
            history_date = (TextView)itemView.findViewById(R.id.history_date);

        }
    }

    private class HistoryBookAdapter extends RecyclerView.Adapter<HistoryBookViewHolder> {

        private ArrayList<HistoryBook> history_book_List = new ArrayList<>();

        public HistoryBookAdapter(ArrayList<HistoryBook> history_book_List) {
            this.history_book_List = history_book_List;
        }

        @Override
        public int getItemCount() {
            return history_book_List.size();
        }

        @Override
        public void onBindViewHolder(HistoryBookViewHolder holder, int position) {
            holder.history_number.setText(String.valueOf(position+1)+".");
            holder.history_name.setText(history_book_List.get(position).historyBookName);
            holder.history_date.setText(history_book_List.get(position).historyDate);
        }

        @Override
        public HistoryBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_book,parent,false);
            return new HistoryBookViewHolder(view);
        }

    }
}
