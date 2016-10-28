package com.estsoft.muvigram.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.util.ViewUtils;


/**
 * Created by gangGongUi on 2016. 10. 28..
 */
public class TransCirclerView extends Button {

    private int mDynamicWidth;
    private int mDynamicHeight;
    private int mImageResource;
    private float mDynamicTextSize;
    private String mDynamicText;
    private Bitmap mImageBitmap;


    public TransCirclerView(Context context) {
        super(context);
    }

    public TransCirclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(getContext(), attrs, 0);
    }

    public TransCirclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(getContext(), attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
        setBackgroundColor(Color.TRANSPARENT);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TransCirclerView, defStyle,
                0);
        final int defSize = ViewUtils.getDisPlay(getContext()).getWidth() * 12 / 100;
        final int textSize = 20;
        mDynamicHeight = a.getInteger(R.styleable.TransCirclerView_dynamic_height, defSize);
        mDynamicWidth = a.getInteger(R.styleable.TransCirclerView_dynamic_width, defSize);
        mImageResource = a.getResourceId(R.styleable.TransCirclerView_dynamic_icon, R.drawable.ic_action_name);
        mDynamicTextSize = a.getDimension(R.styleable.TransCirclerView_dynamic_textSize, 20);
        mDynamicText = a.getString(R.styleable.TransCirclerView_dynamic_text);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = getDisplay().getWidth() * mDynamicWidth / 100;
        final int height = getDisplay().getHeight() * mDynamicHeight / 100;

        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.transCircularButtonColor));
        paint.setAntiAlias(true);
        final int circleX = getMeasuredWidth() / 2;
        final int circleY = getMeasuredHeight() - circleX;
        canvas.drawCircle(circleX, circleY, circleX, paint);


        final Bitmap mResizeBitmap = getResizeBitmap(mImageResource);

        paint.reset();
        canvas.drawBitmap(mResizeBitmap, circleX - mResizeBitmap.getWidth() / 2, circleY - mResizeBitmap.getHeight() / 2, paint);


        final int mI = (getMeasuredHeight() - getMeasuredWidth()) * 22 / 100;


        paint.setColor(Color.WHITE);
        paint.setTextSize(mDynamicTextSize);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(50);
        paint.setTypeface(Typeface.SANS_SERIF);
        canvas.drawText(mDynamicText, circleX, (getMeasuredHeight() - getMeasuredWidth()) / 2 + mI, paint);

//
//        paint.reset();
//
//        canvas.drawBitmap(getResizeBitmap(mImageResource), circleX, circleX, paint);
        //canvas.drawBitMap


    }


    @Override protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private Bitmap getResizeBitmap(int res) {
        if (mImageBitmap != null) {
            return mImageBitmap;
        }
        mImageBitmap = BitmapFactory.decodeResource(getResources(), res);
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        final int icon_size = metrics.widthPixels * 8 / 100;
        mImageBitmap = Bitmap.createScaledBitmap(mImageBitmap, icon_size, icon_size, true);
        return mImageBitmap;
    }
}
