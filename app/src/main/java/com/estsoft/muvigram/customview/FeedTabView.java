package com.estsoft.muvigram.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.MuviGramApplication;
import com.estsoft.muvigram.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gangGongUi on 2016. 10. 18..
 */
public class FeedTabView extends View  {
    private int mBackLineColor;
    private int mActiveColor;
    private int mTextColor;
    private int mTextSize;
    private TabLocationInfo mTabLocationInfo;
    private List<String> mTabTitles = Arrays.asList("follow", "featured", "for you");

    private onFeedTabItemClickListener mOnFeedTabItemClickListener;

    private boolean[] isActiveItem = new boolean[3];


    public FeedTabView(Context context)
    {
        super(context);
    }

    public FeedTabView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.FeedTabView, 0, 0);

        this.mBackLineColor = typedArray.getInteger(R.styleable.FeedTabView_backLineColor, Color.parseColor("#90FAFAFA"));
        this.mActiveColor = typedArray.getInteger(R.styleable.FeedTabView_activeTabColor, Color.WHITE);
        this.mTextColor = typedArray.getInteger(R.styleable.FeedTabView_textColor, Color.WHITE);
        this.mTextSize = typedArray.getInteger(R.styleable.FeedTabView_textSize,  40);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        float tabWidthTemp = width / 5;
        float mTabWidthSize = tabWidthTemp * 3;
        float barPlusSize =  tabWidthTemp * 30 / 100 * 2;
         mTabWidthSize += barPlusSize;
        float backLineStartX = tabWidthTemp * 70 / 100;
        float backLineEndX = width - backLineStartX;
        setPadding(0 , 0, 0 , 0);
        mTabLocationInfo = new TabLocationInfo(mTabWidthSize, backLineStartX, backLineEndX);

        ViewGroup.MarginLayoutParams margins = ViewGroup.MarginLayoutParams.class.cast(getLayoutParams());
        int margin = 0;

        margins.topMargin = ((MuviGramApplication) getContext().getApplicationContext()).getStatusBarHeight();

        Log.e("matgin", margins.topMargin + "");
        margins.bottomMargin = margin;
        margins.leftMargin = margin;
        margins.rightMargin = margin;
        setLayoutParams(margins);
    }

    @Override
    protected void onDraw(Canvas canvas)  {
        super.onDraw(canvas);

        int height =  this.getMeasuredHeight();


        Paint backLinePaint = new Paint();
        backLinePaint.setColor(this.mBackLineColor);
        backLinePaint.setStrokeJoin(Paint.Join.MITER);
        backLinePaint.setStrokeWidth(1); // 3
        backLinePaint.setAntiAlias(true);
        backLinePaint.setShadowLayer(0, 0, 0, 0);
        backLinePaint.setStyle(Paint.Style.FILL);


        canvas.drawLine(mTabLocationInfo.mBackLineStartX, getPaddingTop(), mTabLocationInfo.mBarkLineEndX, getPaddingTop(), backLinePaint);


        Paint textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeJoin(Paint.Join.MITER);
        textPaint.setStrokeWidth(10);
        textPaint.setAntiAlias(true);
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(mTextSize);

        final float tebItemWidth = mTabLocationInfo.mTabWidthSize / 3;
        canvas.drawText(mTabTitles.get(0), tebItemWidth / 2 + mTabLocationInfo.mBackLineStartX, height/ 2, textPaint);
        canvas.drawText(mTabTitles.get(1), tebItemWidth + mTabLocationInfo.mBackLineStartX + (tebItemWidth / 2), height / 2, textPaint );
        canvas.drawText(mTabTitles.get(2), tebItemWidth * 2 + mTabLocationInfo.mBackLineStartX + (tebItemWidth / 2), height / 2, textPaint );
        activeTab(canvas, backLinePaint, textPaint);

    }

    private void activeTab(Canvas canvas, Paint backLinePaint, Paint textPaint) {

        final int height =  this.getMeasuredHeight();
        final float paddingTop = 0;
        final float tebItemWidth = mTabLocationInfo.mTabWidthSize / 3;

        backLinePaint.setStrokeWidth(7);
        backLinePaint.setColor(mActiveColor);
        textPaint.setStrokeWidth(500);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        if(isActiveItem[0]) {
            canvas.drawLine(mTabLocationInfo.mBackLineStartX, getPaddingTop() + paddingTop, mTabLocationInfo.mBackLineStartX + tebItemWidth, getPaddingTop() + paddingTop,  backLinePaint);
            canvas.drawText(mTabTitles.get(0), tebItemWidth / 2 + mTabLocationInfo.mBackLineStartX, height/ 2, textPaint);
            if(mOnFeedTabItemClickListener != null) {
                mOnFeedTabItemClickListener.onItemClick(new BarItem(0, mTabTitles.get(0)));
            }
        } else if (isActiveItem[1]) {
            canvas.drawLine(mTabLocationInfo.mBackLineStartX + tebItemWidth, getPaddingTop() + paddingTop, mTabLocationInfo.mBackLineStartX + tebItemWidth * 2, getPaddingTop() + paddingTop,  backLinePaint);
            canvas.drawText(mTabTitles.get(1), tebItemWidth + mTabLocationInfo.mBackLineStartX + (tebItemWidth / 2), height / 2, textPaint );
            if(mOnFeedTabItemClickListener != null) {
                mOnFeedTabItemClickListener.onItemClick(new BarItem(1, mTabTitles.get(1)));
            }
        } else if(isActiveItem[2]) {
            canvas.drawLine(mTabLocationInfo.mBackLineStartX + tebItemWidth * 2, getPaddingTop() + paddingTop, mTabLocationInfo.mBackLineStartX + tebItemWidth * 3, getPaddingTop() + paddingTop,  backLinePaint);
            canvas.drawText(mTabTitles.get(2), tebItemWidth * 2 + mTabLocationInfo.mBackLineStartX + (tebItemWidth / 2), height / 2, textPaint );
            if(mOnFeedTabItemClickListener != null) {
                mOnFeedTabItemClickListener.onItemClick(new BarItem(2, mTabTitles.get(2)));
            }
        }
    }

    public void setOnFeedTabItemClickListener(onFeedTabItemClickListener onFeedTabItemClickListener)
    {
        mOnFeedTabItemClickListener = onFeedTabItemClickListener;
    }

    public void setTabTitles(String title, String title1, String title2) {
            mTabTitles.clear();
            mTabTitles.add(title);
            mTabTitles.add(title1);
            mTabTitles.add(title2);
            invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        PointF pointF = new PointF();
        pointF.x = event.getX();
        pointF.y = event.getY();

        final float tebItemWidth = mTabLocationInfo.mTabWidthSize / 3;

        if(event.getAction() == MotionEvent.ACTION_UP) {

            final float startLocation = mTabLocationInfo.mBackLineStartX;

            if(startLocation < pointF.x && pointF.x < startLocation+tebItemWidth)
            {
                // click item 0
                setActiveItem(0);
            } else if (startLocation + tebItemWidth < pointF.x && pointF.x < startLocation + tebItemWidth * 2) {
                // click item 1
                setActiveItem(1);
            } else if(startLocation + tebItemWidth * 2 < pointF.x && pointF.x < mTabLocationInfo.mBarkLineEndX) {
                // click item 2
                setActiveItem(2);
            }
        }

        return true;
    }

    public void setActiveItem(int idx) {
        switch (idx) {
            case 0:
                isActiveItem[0] = true;
                isActiveItem[1] = isActiveItem[2] = false;
                break;
            case 1:
                isActiveItem[1] = true;
                isActiveItem[0] = isActiveItem[2] = false;
                break;
            case 2:
                isActiveItem[2] = true;
                isActiveItem[1] = isActiveItem[0] = false;
                break;
            default:
                break;
        }
        invalidate();
    }

    public interface onFeedTabItemClickListener
    {
        void onItemClick(BarItem barItem);
    }

    public class BarItem {
        private int mIndex;
        private String mTitle;

        public BarItem(int index, String title)
        {
            mIndex = index;
            mTitle = title;
        }

        public int getIndex()
        {
            return mIndex;
        }

        public String getTitle()
        {
            return mTitle;
        }
    }

    private class TabLocationInfo {
        public float mTabWidthSize;
        public float mBackLineStartX;
        public float mBarkLineEndX;

        public TabLocationInfo(float tabWidthSize, float backLineStartX, float barkLineEndX)
        {
            mTabWidthSize = tabWidthSize;
            mBackLineStartX = backLineStartX;
            mBarkLineEndX = barkLineEndX;
        }
    }

}
