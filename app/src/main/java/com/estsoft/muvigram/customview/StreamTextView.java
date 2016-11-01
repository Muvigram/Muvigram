package com.estsoft.muvigram.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.util.ViewUtils;


/**
 * Created by gangGongUi on 2016. 10. 31..
 */
public class StreamTextView extends View {

    private int mDynamicWidth;
    private int mDynamicHeight;
    private int mDynamicColor;
    private float mDynamicTextSize;
    private String mDynamicText = "Alone Together - Fall Out Boy";
    private int mPosX = 0;
    private final String SPACE = "        ";
    private final int LOOP_STRING_CNT = 3;


    public StreamTextView(Context context) {
        super(context);
    }

    public StreamTextView(Context context, AttributeSet attrs) {
        super(context, attrs);



        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StreamTextView, 0, 0);
        final int defSize = ViewUtils.getDisplayPerWidth(getContext(), 12);
        final int textSize = 20;
        mDynamicHeight = a.getInteger(R.styleable.StreamTextView_dynamic_height, defSize);
        mDynamicWidth = a.getInteger(R.styleable.StreamTextView_dynamic_width, defSize);
        mDynamicTextSize = a.getDimension(R.styleable.StreamTextView_dynamic_textSize, textSize);
        mDynamicText = a.getString(R.styleable.StreamTextView_dynamic_text);
        for(int i = 0; i < LOOP_STRING_CNT; i++) { mDynamicText += SPACE + mDynamicText; }
        mDynamicColor = a.getColor(R.styleable.StreamTextView_dynamic_textColor, Color.WHITE);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int displayPerWidth = ViewUtils.getDisplayPerWidth(getContext(), MeasureSpec.getSize(mDynamicWidth));
        final int displayPerHeight = ViewUtils.getDisplayPerHeight(getContext(), MeasureSpec.getSize(mDynamicHeight));

        super.onMeasure(MeasureSpec.makeMeasureSpec(displayPerWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(displayPerHeight, MeasureSpec.AT_MOST));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(mDynamicColor);
        paint.setAntiAlias(true);
        paint.setTextSize(mDynamicTextSize);
        canvas.drawText(mDynamicText, mPosX, getMeasuredHeight() / 2, paint);

        mPosX+=2;

        if (mPosX >= getMeasuredWidth()) {
            mPosX %= getMeasuredWidth();
        }

        invalidate();
    }



    public void setText(@NonNull String mDynamicText) {

        for(int i = 0; i < LOOP_STRING_CNT; i++) {
            mDynamicText += SPACE + mDynamicText;
        }
        this.mDynamicText = mDynamicText;
    }


}
