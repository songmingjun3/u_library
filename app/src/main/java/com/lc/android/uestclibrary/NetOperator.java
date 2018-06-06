package com.lc.android.uestclibrary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Lc on 2017/3/24.
 */

public class NetOperator {

    public static final String unNet = "您还没有联网呦";

    public static boolean checkNetwork(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
    }

    //网页分为两种类型
    public static boolean Check_Web(String html) {
        String reg = "<screens/bib_display_chx.html>";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(html);
        if (m.find()) {
            return true;  //book_location 第二个网页
        } else
            return false; //book_list 第一个网页

    }

    //book_list 第一个网页
    public static ArrayList<Book> Extract_BookList(String html) {
        ArrayList<Book> bookList = new ArrayList<>();

        String reg1 = "\\d-\\d+ \\w (\\d+)";
        Pattern p1 = Pattern.compile(reg1);
        Matcher m1 = p1.matcher(html);
        int amount;
        int visible_amount;
        if (m1.find()) {
            amount = Integer.parseInt(m1.group(1));
            visible_amount = (amount > 40) ? 40 : amount;
        } else
            visible_amount = 40;

        String reg2 = "<span class=\"briefcitTitle\"><a href=\"(.*?)\">(.*?)<.*?br />.*?br />(.*?)<";
        Pattern p2 = Pattern.compile(reg2);
        Matcher m2 = p2.matcher(html);


        for (int i = 1; i <= visible_amount; i++) {
            if (m2.find()) {
                bookList.add(new Book(m2.group(1), m2.group(2), m2.group(3)));

            } else
                System.out.println("正则表达式错误");
        }
        return bookList;
    }

    //book_location 第二个网页
    public static ArrayList<Book_location> Extract_BookLocation(String html) {

        ArrayList<Book_location> book_location_list = new ArrayList<>();
        DownloadHtmlResult dhr;

        String reg1 = "<!-- field 1 -->&nbsp;(清水河|沙河|博约书屋).*?(?<=\">)(.*?)(?=</a>).*?&nbsp;(.*?)</td.*?&nbsp;(.*?)</td>";
        Pattern p1 = Pattern.compile(reg1);
        Matcher m1 = p1.matcher(html);

        while (m1.find()) {
            dhr = NetOperator.downloadHtml_GET(LinkIndex.WEB_book_location + m1.group(4), "GBK", null);

            if (dhr.Success) {
                String reg2 = "\\[.*?\\](.*?)\"";
                Pattern p2 = Pattern.compile(reg2);
                Matcher m2 = p2.matcher(dhr.Html);
                if (m2.find())
                    book_location_list.add(new Book_location(m1.group(1), m1.group(2), m1.group(3), m2.group(1)));
                else
                    book_location_list.add(new Book_location(m1.group(1), m1.group(2), m1.group(3), null));
            } else
                book_location_list.add(new Book_location(m1.group(1), m1.group(2), m1.group(3), null));
        }
        return book_location_list;
    }

    //borrow_list
    public static ArrayList<BorrowBook> Extract_BorrowBook(String html){
        ArrayList<BorrowBook> borrow_book_list = new ArrayList<>();

        String reg = "<span class=\"patFuncTitleMain\">(.*?)</span>.*?class=\"patFuncStatus\">(.*?)</td>";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(html);

        while(m1.find()){
            borrow_book_list.add(new BorrowBook(m1.group(1),m1.group(2)));
        }
        return borrow_book_list;

    }

    //readinghistory
    public static ArrayList<HistoryBook> Extract_ReadingHistory(String html){
        ArrayList<HistoryBook> history_book_list = new ArrayList<>();

        String reg = "<span class=\"patFuncTitleMain\">(.*?)</span>.*?class=\"patFuncDate\">(.*?)</td>";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(html);

        while(m1.find()){
            history_book_list.add(new HistoryBook(m1.group(1),m1.group(2)));
        }
        return history_book_list;

    }

    //Q&A
    public static ArrayList<QA> Extract_QA(String html){
        ArrayList<QA> qa_list = new ArrayList<>();

        String reg = "【留言主题】</span>(.*?)（(.*?)）</div>.*?</i>(.*?)</div>.*?</i>(.*?)</div>";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(html);

        while(m1.find()){
            qa_list.add(new QA(m1.group(1),m1.group(2),m1.group(3),m1.group(4)));
        }
        return qa_list;

    }
    //个人信息
    public static ArrayList<String> Extract_Personal(String html){
        ArrayList<String> personalStr = new ArrayList<>();

        String reg = "class=\"patNameAddress\"><strong>(.*?)</strong><br />(.*?)<br />(.*?)<br />";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(html);

        int i = 1;
        while(m1.find()){
            personalStr.add(m1.group(i));
            i++;
        }
        return personalStr;

    }

// --------------------------------------------------------------------------//
    public static class DownloadHtmlResult {
        public final boolean Success;
        public final String Html;
        public final String Cookie;

        public DownloadHtmlResult(boolean success, String html, String cookie) {
            this.Success = success;
            this.Cookie = cookie;
            this.Html = html;
        }
    }

    // 下载 html
    static DownloadHtmlResult downloadHtml_GET(String strUrl, String coded_system, String cookie) {
        StringBuilder pageHTML = new StringBuilder();
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Cookie", cookie);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), coded_system));
            String line;
            while ((line = br.readLine()) != null) {
                pageHTML.append(line);
            }

            connection.disconnect();
            return new DownloadHtmlResult(true, pageHTML.toString(), cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DownloadHtmlResult(false, null, null);
    }


    static DownloadHtmlResult downloadHtml_POST(String strUrl, String paraUrl) {
        DownloadHtmlResult dhr_get;
        StringBuilder pageHTML = new StringBuilder();
        try {
            trustAllHosts(); //信任 https 的 SSL 的 所有认证，暂缓之策。

            URL url = new URL(strUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false); //取消重定向,如果不加这一句，HttpURLConnection是自动重定向的
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            //向网站写入request body
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(paraUrl);
            out.flush();
            out.close();

//            connection.getResponseCode() == 302
            String cookie = connection.getHeaderField("Set-Cookie");
            LinkIndex.person_cookie = cookie;

            String location = connection.getHeaderField("Location");
            String[] str = location.split("/");
            LinkIndex.person_code = str[str.length-2];
            //LinkIndex.login_state = "login";

            dhr_get = downloadHtml_GET(LinkIndex.WEBs_query + location, "utf-8", cookie);
            connection.disconnect();
            return dhr_get;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DownloadHtmlResult(false, null, null);
    }


    private static void trustAllHosts() {

        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {

            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {

            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

