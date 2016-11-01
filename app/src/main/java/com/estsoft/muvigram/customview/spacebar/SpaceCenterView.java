package com.estsoft.muvigram.customview.spacebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by gangGongUi on 2016. 10. 20..
 */
public class SpaceCenterView extends View implements OnClickListener {

    private OnSpaceCenterClickListener mSpaceCenterClick;

    public SpaceCenterView(Context context, OnSpaceCenterClickListener spaceCenterClick) {
        super(context);
        this.mSpaceCenterClick = spaceCenterClick;
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#EDC917"));

        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredHeight() / 2 - 8, paint);

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);

        final float length = getMeasuredHeight() * 35 / 100;
        final float y = getMeasuredHeight() / 2;
        final float x = getMeasuredWidth() / 2;

        canvas.drawLine(getMeasuredWidth() / 2 - (length / 2), y, getMeasuredWidth() / 2 + length / 2, y, paint);
        canvas.drawLine(x, getMeasuredHeight() / 2 - length / 2, x, getMeasuredHeight() / 2 + length / 2, paint);

    }

    @Override
    public void onClick(View v) {
        if (mSpaceCenterClick != null) {
            mSpaceCenterClick.onCenterClick();
        }
    }

    interface OnSpaceCenterClickListener {
        void onCenterClick();

    }
}
