package com.example.admin.mydailystudy.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.example.admin.mydailystudy.R;

public class SimplePaddingDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint dividerPaint;
    private int childPosition;

    public SimplePaddingDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.gray));
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        childPosition = parent.getChildPosition(view);
//        if (childPosition == 0) {
//            outRect.bottom = 0;
//        } else {
//            outRect.bottom = dividerHeight;
//        }
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < childCount - 1; i++) {
            View childAt = parent.getChildAt(i);
            if (!(childAt instanceof RelativeLayout)) {
                float top = childAt.getBottom();
                float bottom = childAt.getBottom() + dividerHeight;
                c.drawRect(left, top, right, bottom, dividerPaint);
            }
        }
    }
}
