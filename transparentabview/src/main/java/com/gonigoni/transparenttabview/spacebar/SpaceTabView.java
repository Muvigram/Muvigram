package com.gonigoni.transparenttabview.spacebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by gangGongUi on 2016. 10. 20..
 */
public class SpaceTabView extends ImageButton implements View.OnClickListener {

    private int index;
    private boolean isClick = false;
    private OnSpaceTabClickListener mOnSpaceTabClickListener;

    public SpaceTabView(Context context, OnSpaceTabClickListener onSpaceTabClickListener, int index) {
        super(context);
        this.mOnSpaceTabClickListener = onSpaceTabClickListener;
        this.index = index;
        setOnClickListener(this);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isClick && index == 0) {
            Paint backLinePaint = new Paint();
            backLinePaint.setColor(Color.WHITE);
            backLinePaint.setStrokeJoin(Paint.Join.MITER);
            backLinePaint.setStrokeWidth(30);
            backLinePaint.setAntiAlias(true);
            backLinePaint.setShadowLayer(0, 0, 0, 0);
            backLinePaint.setStyle(Paint.Style.FILL);
            canvas.drawLine(getPaddingLeft(), getMeasuredHeight(), getPaddingLeft() + getMeasuredWidth(), getMeasuredHeight(), backLinePaint);
        }

    }

    public int getIndex() {
        return index;
    }

    @Override
    public void onClick(View v) {
        if (mOnSpaceTabClickListener != null) {
            mOnSpaceTabClickListener.onSpaceTabClick(this);
        }
        isClick = true;
        invalidate();
    }

    public void setActiveBackgroundColor() {
        setBackgroundColor(Color.parseColor("#1C1C1C"));
    }

    public void setUnActiveBackgroundColor() {
        isClick = false;
        invalidate();
        setBackgroundColor(Color.TRANSPARENT);
    }

    interface OnSpaceTabClickListener {
        void onSpaceTabClick(SpaceTabView spaceTabView);
    }
}
