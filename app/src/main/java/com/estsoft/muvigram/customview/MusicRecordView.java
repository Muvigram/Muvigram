package com.estsoft.muvigram.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by gangGongUi on 2016. 10. 27..
 */
public class MusicRecordView extends ImageView {

    private final String RECODE_COLOR = "#90000000";
    private final String RECODE_IN_COLOR = "#F0CA21";


    public MusicRecordView(Context context) {
        super(context);
        init();
    }

    public MusicRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MusicRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //getDisplay().getRealSize(mPoint);
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDisplay().getWidth() * 15 / 100;
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor(RECODE_COLOR));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getMeasuredWidth() * 17 / 100, paint);
        paint.setColor(Color.parseColor(RECODE_IN_COLOR));
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, (float) (getMeasuredWidth() * 7.5 / 100), paint);

    }


}
