package com.example.admin.mydailystudy.mvp.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalScrollViewPager extends ViewPager {

    private float startX, startY, endX, endY;

    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父亲视图不拦截当前控件的事件
        getParent().requestDisallowInterceptTouchEvent(false);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                getParent().requestDisallowInterceptTouchEvent(true);//都把事件传给控件
                //记录起始坐标
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                //新的坐标
                endX = ev.getX();
                endY = ev.getY();

                //计算偏移量
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    //水平方向滑动
                    if (getCurrentItem() == 0 && distanceX > 0) {//第0个而且从左到右滑动（从左到右滑动 distanceX > 0）
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if (getCurrentItem() == (getAdapter().getCount() - 1) && distanceX < 0){ //最后一个而且从右到左滑动（从右到左滑动 distanceX < 0）
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    //其他，中间部分
                    else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                }else {
                    //竖直方向滑动
                }

                break;
            case MotionEvent.ACTION_UP :
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
