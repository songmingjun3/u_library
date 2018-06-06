package com.lc.android.uestclibrary;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/5/4.
 */

public class SingletonBorrowBook {

    private ArrayList<BorrowBook> data;
    private SingletonBorrowBook(){

    }
    private static final SingletonBorrowBook singleton = new SingletonBorrowBook();
    public static SingletonBorrowBook getInstance(){
        return  singleton;
    }
    public ArrayList<BorrowBook> getData(){
        return data;
    }
    public void setData(ArrayList<BorrowBook> data){
        this.data = data;
    }

}

class BorrowBook{
    public String borrowBookName;
    public String date;

    public BorrowBook(String borrowBookName, String date) {
        this.borrowBookName = borrowBookName;
        this.date = date;
    }
}
