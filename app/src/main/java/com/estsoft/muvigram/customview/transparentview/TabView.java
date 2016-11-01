package com.estsoft.muvigram.customview.transparentview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by gangGongUi on 2016. 10. 19..
 */
public class TabView extends Button implements OnClickListener {

    private final int index;
    private int mWidth;
    private int mHeight;
    private int mTextColor;
    private int mActiveTabColor;
    private int mTextSize;
    private String mTabText;
    private boolean isActive;
    private OnTabClickListenerWithTransParentTab mOnTabClickListenerWithTransParentTab;

    /**
     * Instantiates a new Tab view.
     *
     * @param context        the context
     * @param index          the index
     * @param textColor      the text color
     * @param activeTabColor the active tab color
     * @param textSize       the text size
     * @param tabText        the tab text
     */
    public TabView(Context context, int index, int textColor, int activeTabColor, int textSize, String tabText) {
        super(context);
        this.mTextColor = textColor;
        this.mActiveTabColor = activeTabColor;
        this.mTextSize = textSize;
        this.mTabText = tabText;
        this.index = index;
        init();
    }


    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeJoin(Paint.Join.MITER);
        textPaint.setStrokeWidth(10);
        textPaint.setAntiAlias(true);
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(mTextSize);

        canvas.drawText(mTabText, mWidth / 2, (float) (mHeight / 2.4), textPaint);

        if (isActive) {
            textPaint.setStrokeWidth(50);
            textPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(mTabText, mWidth / 2, (float) (mHeight / 2.4), textPaint);

            Paint backLinePaint = new Paint();
            backLinePaint.setColor(mActiveTabColor);
            backLinePaint.setStrokeJoin(Paint.Join.MITER);
            backLinePaint.setStrokeWidth(20);
            backLinePaint.setAntiAlias(true);
            backLinePaint.setShadowLayer(0, 0, 0, 0);
            backLinePaint.setStyle(Paint.Style.FILL);

            canvas.drawLine(getPaddingLeft(), 0, getPaddingLeft() + getMeasuredWidth(), 0, backLinePaint);

        }
    }

    @Override
    public CharSequence getText() {
        return mTabText;
    }

    /**
     * Gets tab text.
     *
     * @return the tab text
     */
    public String getTabText() {
        return mTabText;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    @Override
    final public void onClick(View view) {
        isActive = true;
        if (mOnTabClickListenerWithTransParentTab != null && view != null) {
            mOnTabClickListenerWithTransParentTab.onTabClick(this);
        }
        invalidate();
    }

    /**
     * Sets on tab click listener with trans parent tab.
     *
     * @param onTabClickListenerWithTransParentTab the on tab click listener with trans parent tab
     */
    protected void setOnTabClickListenerWithTransParentTab(OnTabClickListenerWithTransParentTab onTabClickListenerWithTransParentTab) {
        mOnTabClickListenerWithTransParentTab = onTabClickListenerWithTransParentTab;
    }

    /**
     * Init active line.
     */
    protected void initActiveLine() {
        this.isActive = false;
        invalidate();
    }

    /**
     * The interface On tab click listener with trans parent tab.
     */
    protected interface OnTabClickListenerWithTransParentTab {
        /**
         * On tab click.
         *
         * @param tabView the tab view
         */
        void onTabClick(TabView tabView);
    }
}
