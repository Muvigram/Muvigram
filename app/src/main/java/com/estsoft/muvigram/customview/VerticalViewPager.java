package com.estsoft.muvigram.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by gangGongUi on 2016. 10. 17..
 */
public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        super(context);
        setPageTransformer(false, new Trasformer());
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new Trasformer());
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;

        event.setLocation(swappedX, swappedY);

        return event;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(ev));
        swapTouchEvent(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }

    class Trasformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            page.setTranslationX(page.getWidth() * -position);
            float yPosition = position * page.getHeight();
            page.setTranslationY(yPosition);
        }
    }
}
