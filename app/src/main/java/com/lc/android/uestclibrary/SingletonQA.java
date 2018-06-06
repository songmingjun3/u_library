package com.lc.android.uestclibrary;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/6/26.
 */

public class SingletonQA {
    private ArrayList<QA> data;
    private SingletonQA(){

    }
    private static final SingletonQA singleton = new SingletonQA();
    public static SingletonQA getInstance(){
        return  singleton;
    }
    public ArrayList<QA> getData(){
        return data;
    }
    public void setData(ArrayList<QA> data){
        this.data = data;
    }


}

class QA{
    public String theme;
    public String time;
    public String question;
    public String answer;

    public QA(String theme, String time,String question, String answer) {
        this.answer = answer;
        this.time = time;
        this.question = question;
        this.theme = theme;
    }
}
