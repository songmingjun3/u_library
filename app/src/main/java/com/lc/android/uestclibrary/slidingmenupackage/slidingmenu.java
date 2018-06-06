package com.lc.android.uestclibrary.slidingmenupackage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by UPC on 2017/6/25.
 */

public class slidingmenu extends HorizontalScrollView {

    private int mScreenWidth;
    private int mMenuRightPadding; //默认菜单距离右边边距为50dp
    private boolean once = false; //只设置子view一次
    private LinearLayout mWrapper;
    private ViewGroup leftMenu;  //左滑菜单
    private ViewGroup mainContent;  //图书馆主页面
    private int leftmenuWidth;
    public boolean IsShowleftMenu = false;
    private Button showButton;

    public slidingmenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        //把单位dp转化为px
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,context.getResources().getDisplayMetrics());
    }

    //设置子view的长宽
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        if (!once)
        {
            mWrapper = (LinearLayout) getChildAt(0);
            leftMenu = (ViewGroup) mWrapper.getChildAt(0);
            mainContent = (ViewGroup) mWrapper.getChildAt(1);
            leftmenuWidth = mScreenWidth - mMenuRightPadding;
            leftMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mainContent.getLayoutParams().width = mScreenWidth;
            once = true;
        };
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //通过设置偏移量使开始显示content,menu在左边
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        this.scrollTo(leftmenuWidth,0);
        IsShowleftMenu = true;
        super.onLayout(changed, l, t, r, b);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(scrollX > leftmenuWidth / 2)
                {
                    this.smoothScrollTo(leftmenuWidth,0);
                    this.IsShowleftMenu = false;
                }
                else
                {
                    this.smoothScrollTo(0,0);
                    this.IsShowleftMenu = true;
                };
                return true;


        };
        return super.onTouchEvent(ev);
    }







}
