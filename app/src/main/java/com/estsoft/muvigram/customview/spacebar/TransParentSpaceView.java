package com.estsoft.muvigram.customview.spacebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by gangGongUi on 2016. 10. 20..
 */
public class TransParentSpaceView extends LinearLayout implements SpaceCenterView.OnSpaceCenterClickListener, SpaceTabView.OnSpaceTabClickListener {

    private final int MAX_ITEM_COUNT = 5;
    private final int CENTER_ITEM_IDX = 2;
    private final int PAINT_STOCK = 3;
    private final int TRANSPARENT_COLOR_CHANGE_DELAY = 1;
    private final String DEFAULT_COLOR = "#262727";
    private int mTabButtonCnt = 0;
    private OnSpaceViewListener mOnSpaceViewListener;

    /**
     * Instantiates a new Trans parent space view.
     *
     * @param context the context
     */
    public TransParentSpaceView(Context context) {
        super(context);
    }

    /**
     * Instantiates a new Trans parent space view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public TransParentSpaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.TRANSPARENT);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height =  getDisplay().getHeight() * 9 / 100;
        final int width = getDisplay().getWidth();
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        final int childCount = getChildCount();

        if (childCount == MAX_ITEM_COUNT) {

            final int childWidth = getMeasuredWidth() / childCount;

            int curLeft = getPaddingLeft();
            int curRight = getPaddingLeft() + childWidth;

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);

                child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.AT_MOST));

                child.layout(curLeft, getPaddingTop() + PAINT_STOCK, curRight, getMeasuredHeight());

                curLeft += childWidth;
                curRight += childWidth;

            } // FOR END

        } // 46 LINE IF END
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 상단 선
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(90);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(PAINT_STOCK);
        canvas.drawLine(getLeft(), getPaddingTop(), getRight(), getPaddingTop(), paint);
    }


    /**
     * Add space bar icon.
     *
     * @param res the res
     */
    public void addSpaceBarIcon(@NonNull int res) {

        final int childCount = getChildCount();

        if (childCount == CENTER_ITEM_IDX) {
            SpaceCenterView centerView = new SpaceCenterView(getContext(), this);
            addView(centerView);
        }

        if (childCount <= MAX_ITEM_COUNT) {
            SpaceTabView spaceTabView = new SpaceTabView(getContext(), this, mTabButtonCnt++);
            spaceTabView.setImageBitmap(getResizeBitmap(res));
            addView(spaceTabView);
        }

    }

    @Override
    public void onCenterClick() {
        if (mOnSpaceViewListener != null) {
            mOnSpaceViewListener.onCenterClick();
        }
    }

    @Override
    public void onSpaceTabClick(SpaceTabView spaceTabView) {


        if (spaceTabView.getIndex() == 0) {
            resetSpaceTabView();
            Observable.timer(TRANSPARENT_COLOR_CHANGE_DELAY, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .onBackpressureDrop()
                    .subscribe((longs) -> {
                        setBackgroundColor(Color.TRANSPARENT);
                    });
        } else {
            setBackgroundColor(Color.parseColor(DEFAULT_COLOR));
            resetSpaceTabView();
            spaceTabView.setActiveBackgroundColor();
        }

        if (mOnSpaceViewListener != null) {
            mOnSpaceViewListener.onSpaceTabClick(spaceTabView.getIndex());
        }
    }

    private void resetSpaceTabView() {
        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            if (i != CENTER_ITEM_IDX) {
                View view = getChildAt(i);
                ((SpaceTabView) view).setUnActiveBackgroundColor();
            }
        }
    }

    private Bitmap getResizeBitmap(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();


        final int icon_size = metrics.widthPixels * 7 / 100;
        bitmap = Bitmap.createScaledBitmap(bitmap, icon_size, icon_size, true);
        return bitmap;
    }

    public void setOnSpaceViewListener(OnSpaceViewListener onSpaceViewListener) {
        mOnSpaceViewListener = onSpaceViewListener;
    }

    /**
     * The interface On space view listener.
     */
    public interface OnSpaceViewListener {
        void onCenterClick();

        void onSpaceTabClick(int index);
    }
}
