package com.estsoft.muvigram.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by estsoft on 2016-10-28.
 */

public class ScrollViewNoFling extends HorizontalScrollView {

    @Override
    public void fling(int velocityX) {

    }

    public ScrollViewNoFling(Context context) {
        super(context);
    }

    public ScrollViewNoFling(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewNoFling(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
