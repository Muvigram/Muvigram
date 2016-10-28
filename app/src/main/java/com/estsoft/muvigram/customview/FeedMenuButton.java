package com.estsoft.muvigram.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by gangGongUi on 2016. 10. 27..
 */
public class FeedMenuButton extends Button {

    public FeedMenuButton(Context context) {
        super(context);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public FeedMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);


        final float radius = (float) (getMeasuredWidth() * 5.3 / 100);

        final int widthDistance = getMeasuredWidth() * 15 / 100;

        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius, paint);

        canvas.drawCircle(getMeasuredWidth() / 2 - widthDistance, getMeasuredHeight() / 2, radius, paint);
        canvas.drawCircle(getMeasuredWidth() / 2 + widthDistance, getMeasuredHeight() / 2, radius, paint);


    }
}
