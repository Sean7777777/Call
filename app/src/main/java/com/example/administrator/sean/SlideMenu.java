package com.example.administrator.sean;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/11/6.
 */

public class SlideMenu extends HorizontalScrollView{
    private LinearLayout mWapper;
    private ViewGroup mMenu,mContent;
    private int mScreenWidth;
    private int RightPadding;
    private int MenuWidth;
    private boolean once;

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context,attrs);
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth=outMetrics.widthPixels;
        RightPadding= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,50,context.getResources().getDisplayMetrics());
    }
    //设置子VIEW的宽和高
    //设置自己的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        if(!once){
            mWapper= (LinearLayout) getChildAt(0);
            mMenu= (ViewGroup) mWapper.getChildAt(0);
            mContent= (ViewGroup) mWapper.getChildAt(1);
            MenuWidth=mMenu.getLayoutParams().width=mScreenWidth-RightPadding;
            mContent.getLayoutParams().width=mScreenWidth;
            once=true;
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed,int l,int t,int r,int b){
        super.onLayout(changed,l,t,r,b);
        if(changed){
            this.scrollTo(MenuWidth,0);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent m){
        int action=m.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX=getScrollX();
                if(scrollX>=MenuWidth/2){
                    this.smoothScrollTo(MenuWidth,0);
                }else{
                    this.smoothScrollTo(0,0);
                }
                return true;
        }
        return super.onTouchEvent(m);
    }
}
