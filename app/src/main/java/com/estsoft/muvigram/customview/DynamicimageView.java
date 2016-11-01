package com.estsoft.muvigram.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.estsoft.muvigram.R;

/**
 * Created by gangGongUi on 2016. 10. 28..
 */
public class DynamicImageView extends ImageView {

    private int mDynamicWidth;
    private int mDynamicHeight;

    public DynamicImageView(Context context) {
        super(context);
    }

    public DynamicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public DynamicImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
        setBackgroundColor(Color.TRANSPARENT);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TransCircleView, defStyle,
                0);
        //final int defSize = ViewUtils.getDisPlay(getContext()).getWidth() * 12 / 100;
        mDynamicHeight = a.getInteger(R.styleable.TransCircleView_dynamic_height, -1);
        mDynamicWidth = a.getInteger(R.styleable.TransCircleView_dynamic_width, -1);
        a.recycle();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = getDisplay().getHeight() * mDynamicHeight / 100;
        int width = getDisplay().getWidth() * mDynamicWidth / 100;

        if (mDynamicHeight == -1) {
            height = width;
        } else if (mDynamicWidth == -1) {
            width = height;
        } else if (mDynamicHeight == -1 && mDynamicWidth == -1) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
    }


}
