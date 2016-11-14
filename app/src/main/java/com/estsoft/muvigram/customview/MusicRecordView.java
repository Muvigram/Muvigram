package com.estsoft.muvigram.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.util.ViewUtils;

/**
 * Created by gangGongUi on 2016. 10. 27..
 */
public class MusicRecordView extends ImageView {

    private static final String RECODE_COLOR = "#90000000";
    private static final String RECODE_IN_COLOR = "#F0CA21";
    private int mDynamicWidth;
    private int mDynamicHeight;
    private int mRecodeBackGroundColor;
    private int mCenterAxisColor;
    private boolean isSquare;

    public MusicRecordView(Context context) {
        super(context);
    }

    public MusicRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MusicRecordView, 0, 0);
        final int defSize = ViewUtils.getDisplayPerWidth(getContext(), R.integer.music_record_default_size);
        mDynamicHeight = a.getInteger(R.styleable.MusicRecordView_dynamic_height, defSize);
        mDynamicWidth = a.getInteger(R.styleable.MusicRecordView_dynamic_width, defSize);
        isSquare = a.getBoolean(R.styleable.MusicRecordView_isSquare, false);
        mRecodeBackGroundColor = a.getColor(R.styleable.MusicRecordView_backgroundColor, Color.BLACK);
        mCenterAxisColor = a.getColor(R.styleable.MusicRecordView_centerAxisColor, Color.YELLOW);
        a.recycle();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isSquare) {
            final int size = Math.min(ViewUtils.getDisplayPerWidth(getContext(), mDynamicWidth),
                    ViewUtils.getDisplayPerHeight(getContext(), mDynamicHeight)) ;
            super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST));
        } else {
            final int width = ViewUtils.getDisplayPerWidth(getContext(), mDynamicWidth);
            final int height = ViewUtils.getDisplayPerHeight(getContext(), mDynamicHeight);
            super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(mRecodeBackGroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() * 17 / 100, paint);
        paint.setColor(mCenterAxisColor);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, (float) (getMeasuredWidth() * 7.5 / 100), paint);
    }

}
