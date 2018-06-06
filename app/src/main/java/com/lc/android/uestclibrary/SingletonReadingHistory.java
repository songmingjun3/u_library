package com.lc.android.uestclibrary;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/6/27.
 */

public class SingletonReadingHistory {
    private ArrayList<HistoryBook> data;
    private SingletonReadingHistory(){

    }
    private static final SingletonReadingHistory singleton = new SingletonReadingHistory();
    public static SingletonReadingHistory getInstance(){
        return  singleton;
    }
    public ArrayList<HistoryBook> getData(){
        return data;
    }
    public void setData(ArrayList<HistoryBook> data){
        this.data = data;
    }


}

class HistoryBook{
    public String historyBookName;
    public String historyDate;

    public HistoryBook(String historyBookName, String historyDate) {
        this.historyBookName = historyBookName;
        this.historyDate = historyDate;
    }
}