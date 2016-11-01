package com.estsoft.muvigram.customview.transparentview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.estsoft.muvigram.R;


/**
 * Created by gangGongUi on 2016. 10. 18..
 */
public class TransParentTabView extends LinearLayout implements TabView.OnTabClickListenerWithTransParentTab {

    private final int ITEM_LIMIT = 6;
    private int mItemCount = 0;
    private int mTextSize;
    private int mTextColor;
    private int mBackLineColor;
    private int mActiveTabColor;
    private Context mContext;
    private OnTabItemClickListener mOnTabItemClickListener;

    /**
     * Instantiates a new Trans parent tab view.
     *
     * @param context the context
     */
    public TransParentTabView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Trans parent tab view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public TransParentTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TransParentTabView, 0, 0);
        try {

            this.mTextSize = typedArray.getInteger(R.styleable.TransParentTabView_textSize, 40);
            this.mTextColor = typedArray.getColor(R.styleable.TransParentTabView_textColor, Color.WHITE);
            this.mActiveTabColor = typedArray.getColor(R.styleable.TransParentTabView_activeTabColor, Color.WHITE);
            this.mBackLineColor = typedArray.getColor(R.styleable.TransParentTabView_backLineColor, Color.parseColor("#90FAFAFA"));
            this.mContext = context;
        } finally {
            typedArray.recycle();
            setOrientation(LinearLayout.HORIZONTAL);
            init();
        }
    }

    private void init() {
        setClickable(true);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(getDisplay().getHeight() * 9 / 100, MeasureSpec.AT_MOST));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        final int count = getChildCount();

        if (count > 0) {
            final int childWidth = getMeasuredWidth() / count;

            int curLeft = getPaddingLeft();
            int curRight = getPaddingLeft() + childWidth;

            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);

                child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.AT_MOST));

                child.layout(curLeft, getTop(), curRight, getMeasuredHeight());
                curLeft += childWidth;
                curRight += childWidth;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint backLinePaint = new Paint();
        backLinePaint.setColor(this.mBackLineColor);
        backLinePaint.setStrokeJoin(Paint.Join.MITER);
        backLinePaint.setStrokeWidth(3); // 3
        backLinePaint.setAntiAlias(true);
        backLinePaint.setShadowLayer(0, 0, 0, 0);
        backLinePaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(getPaddingLeft(), getTop(), getMeasuredWidth(), getTop(), backLinePaint);

    }

    /**
     * Add tab item.
     *
     * @param title the title
     */
    public void addTabItem(@NonNull String title) {
        if (!(mItemCount >= ITEM_LIMIT)) {
            TabView tabView = new TabView(mContext, mItemCount, mTextColor, mActiveTabColor, mTextSize, title);
            tabView.setOnTabClickListenerWithTransParentTab(this);
            addView(tabView);
            mItemCount++;
        } else {
            Log.e("TransParentTabView", "You can add up to 6 items!");
        }
    }

    /**
     * Sets active tab.
     *
     * @param idx the idx
     */
    public void setActiveTab(int idx) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TabView child = ((TabView) getChildAt(i));
            if (child.getIndex() == idx) {
                child.onClick(null);
            }
        }
    }

    @Override
    public void onTabClick(TabView tabView) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TabView child = ((TabView) getChildAt(i));
            if (child != tabView) {
                child.initActiveLine();
            }
        }
        if (mOnTabItemClickListener != null) {
            mOnTabItemClickListener.onTabItemClick(tabView);
        }
    }

    /**
     * Sets on tab item click listener.
     *
     * @param onTabItemClickListener the on tab item click listener
     */
    public void setOnTabItemClickListener(OnTabItemClickListener onTabItemClickListener) {
        mOnTabItemClickListener = onTabItemClickListener;
    }

    /**
     * The interface On tab item click listener.
     */
    public interface OnTabItemClickListener {
        /**
         * On tab item click.
         *
         * @param tabView the tab view
         */
        void onTabItemClick(TabView tabView);
    }
}
