package com.lc.android.uestclibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lc.android.uestclibrary.slidingmenupackage.slidingmenu;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    public EditText editTextBookName;
    public ImageButton buttonQuery;

    public LinearLayout MoveToLogin;
    public LinearLayout MoveToQA;
    public LinearLayout MoveToReadinginfo;
    public LinearLayout MoveToReadinghistory;

    public String searchType;
    private Button quitLogin;
    private RadioGroup radioGroup;
    private RadioButton keywordRadioButton;
    private RadioButton nameRadioButton;
    private RadioButton authorRadioButton;
    private RadioButton themeRadioButton;
    //实例化slidingmenu
    private slidingmenu mslidingmenu;
    private TextView slingdingLogin;
   //屏幕宽度
    private int mScreenWidth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        editTextBookName = (EditText) findViewById(R.id.editText_bookName);
        buttonQuery = (ImageButton) findViewById(R.id.query_button);

        MoveToLogin = (LinearLayout) findViewById(R.id.move_to_login);
        MoveToQA = (LinearLayout) findViewById(R.id.QA);
        MoveToReadinginfo = (LinearLayout) findViewById(R.id.slingding_readinginfo);
        MoveToReadinghistory = (LinearLayout) findViewById(R.id.slingding_readinghistory);
        slingdingLogin = (TextView)findViewById(R.id.slingding_login);
        quitLogin = (Button) findViewById(R.id.quit_login);



        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        keywordRadioButton = (RadioButton)findViewById(R.id.KeyWord);
        nameRadioButton = (RadioButton)findViewById(R.id.Name);
        authorRadioButton = (RadioButton)findViewById(R.id.Author);
        themeRadioButton = (RadioButton)findViewById(R.id.Theme);

        keywordRadioButton.setChecked(true);


        mslidingmenu = (slidingmenu) findViewById(R.id.mHorizontalScrollView);


        //获取屏幕宽度
        WindowManager windowManager = this.getWindowManager();
        mScreenWidth = windowManager.getDefaultDisplay().getWidth();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == keywordRadioButton.getId())
                    searchType = "X";
                else if(checkedId == nameRadioButton.getId())
                    searchType = "t";
                else if(checkedId == authorRadioButton.getId())
                    searchType = "a";
                else if(checkedId == themeRadioButton.getId())
                    searchType = "d";
            }
        });

        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //error_info.setVisibility(View.INVISIBLE);
                if(Utils.isFastDoubleClick())
                    return;
                if(NetOperator.checkNetwork(SearchActivity.this)){
                    AsyncQueryBooks asyncTask = new AsyncQueryBooks(SearchActivity.this);
                    asyncTask.execute(searchType);
                }
               else{
                    Toast.makeText(SearchActivity.this, NetOperator.unNet, Toast.LENGTH_SHORT).show();
                }
            }
        });

        MoveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slingdingLogin.getText().toString().equals("未登录")) {
                    Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                    SearchActivity.this.startActivity(intent);
                    SearchActivity.this.finish();
                }
                else
                    Toast.makeText(SearchActivity.this,"您已登录，请先退出登录后重新登录",Toast.LENGTH_LONG).show();


            }
        });

        ArrayList<String> usernumber =this.getIntent().getStringArrayListExtra("personalStr");
        if (usernumber!=null) {
            slingdingLogin.setText(usernumber.get(0));
            quitLogin.setText("退出登录");
            quitLogin.setBackgroundResource(R.drawable.button_background_red);

        }
        quitLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                LinkIndex.person_code = null;
                LinkIndex.person_cookie = null;
                slingdingLogin.setText("未登录");
                quitLogin.setText("");
                quitLogin.setBackgroundResource(R.drawable.button_background_clear);
                Toast.makeText(SearchActivity.this,"已退出登录",Toast.LENGTH_SHORT).show();
            }
        });
        MoveToQA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AsyncQandA asyncTask = new AsyncQandA(SearchActivity.this);
                asyncTask.execute();

            }
        });

        MoveToReadinginfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (slingdingLogin.getText().toString().equals("未登录")){
                 Toast.makeText(SearchActivity.this,"您还没有登录",Toast.LENGTH_SHORT).show();
                }
                else {
                    AsyncBorrowBook asyncTask = new AsyncBorrowBook(SearchActivity.this);
                    asyncTask.execute();
                }

            }
        });

        MoveToReadinghistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (slingdingLogin.getText().toString().equals("未登录")){
                    Toast.makeText(SearchActivity.this,"您还没有登录",Toast.LENGTH_SHORT).show();
                }
                else {
                    AsyncReadingHistory asyncTask = new AsyncReadingHistory(SearchActivity.this);
                    asyncTask.execute();
                }

            }
        });


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                LinkIndex.hideKeyboard(ev,view,SearchActivity.this);
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    public void showleftMenu(View view)
    {
        if(!mslidingmenu.IsShowleftMenu)
        {
            mslidingmenu.smoothScrollTo(0, 0);
            mslidingmenu.IsShowleftMenu = true;
        }
        else
        {
            mslidingmenu.smoothScrollTo(mScreenWidth,0);
            mslidingmenu.IsShowleftMenu = false;
        }

    }

    public void gotoBookCollectionActivity(View view)
    {
        Intent intent = new Intent(SearchActivity.this, BookCollectionListActivity.class);
        SearchActivity.this.startActivity(intent);
    }



}
