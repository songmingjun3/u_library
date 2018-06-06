package com.lc.android.uestclibrary;

import java.util.ArrayList;

/**
 * Created by Lc on 2017/4/14.
 */

public class SingletonBook{
    private ArrayList<Book> data;
    private SingletonBook(){

    }
    private static final SingletonBook singleton = new SingletonBook();
    public static SingletonBook getInstance(){
        return  singleton;
    }
    public ArrayList<Book> getData(){
        return data;
    }
    public void setData(ArrayList<Book> data){
        this.data = data;
    }

}

class Book {
    public String link;
    public String name;
    public String publishing_house;

    public Book(String link,String name,String publishing_house){
        this.link = link;
        this.name = name;
        this.publishing_house = publishing_house;
    }
}
class Singletonbooklocation{
    private ArrayList<Book_location> data;

    private Singletonbooklocation() {

    }
    private static final Singletonbooklocation singleton = new Singletonbooklocation();
    public static Singletonbooklocation getInstance(){
        return  singleton;
    }
    public ArrayList<Book_location> getData(){
        return data;
    }
    public void setData(ArrayList<Book_location> data){
        this.data = data;
    }
}
class Book_location{
    public String place;
    public String retrieve_number;
    public String book_state;
    public String barcode;
    public String location;

    public Book_location(String place, String retrieve_number, String book_state, String location) {
        this.place = place;
        this.retrieve_number = retrieve_number;
        this.book_state = book_state;
        this.location = location;

    }


}




