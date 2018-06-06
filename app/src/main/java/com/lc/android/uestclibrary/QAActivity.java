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

public class QAActivity extends AppCompatActivity {

    private RecyclerView qaRecycler;
    private QandAAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qalist);

        ArrayList<QA> qa_list = SingletonQA.getInstance().getData();

        qaRecycler = (RecyclerView)findViewById(R.id.qa_list);
        qaRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QandAAdapter(qa_list);
        qaRecycler.setAdapter(mAdapter);
    }

    private class QandAViewHolder extends RecyclerView.ViewHolder {
        private TextView qa_theme;
        private TextView qa_time;
        private TextView qa_question;
        private TextView qa_answer;

        public QandAViewHolder(View itemView) {
            super(itemView);

            qa_theme = (TextView)itemView.findViewById(R.id.qa_theme);
            qa_time = (TextView)itemView.findViewById(R.id.qa_time);
            qa_question = (TextView)itemView.findViewById(R.id.qa_question);
            qa_answer = (TextView)itemView.findViewById(R.id.qa_answer);
        }
    }

    private class QandAAdapter extends RecyclerView.Adapter<QandAViewHolder>{

        private ArrayList<QA> qa_list = new ArrayList<>();

        public QandAAdapter(ArrayList<QA> qa_list){
            this.qa_list = qa_list;
        }

        @Override
        public int getItemCount() {
            return qa_list.size();
        }

        @Override
        public void onBindViewHolder(QandAViewHolder holder, int position) {
            holder.qa_theme.setText(qa_list.get(position).theme);
            holder.qa_time.setText(qa_list.get(position).time);
            holder.qa_question.setText(qa_list.get(position).question);
            holder.qa_answer.setText(qa_list.get(position).answer);

        }

        @Override
        public QandAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qa_item,parent,false);
            return new QandAViewHolder(view);
        }
    }


}
