package com.estsoft.muvigram.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.util.ViewUtils;


/**
 * Created by gangGongUi on 2016. 10. 28..
 */
public class TransCircleView extends Button {

    private final int DEFAULT_WIDTH_HEIGHT_PER = 12;
    private final int DEFAULT_TEXT_SIZE = 20;
    private int mDynamicWidth;
    private int mDynamicHeight;
    private int mImageResource;
    private float mDynamicTextSize;
    private String mDynamicText;
    private Bitmap mImageBitmap;
    private boolean isLike = false;
    private final String mDefaultColor = "#40000000";
    private final String mLikeColor = "#ff2d6f";
    private String mBackGroundColor = mDefaultColor;


    public TransCircleView(Context context) {
        super(context);
    }

    public TransCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(getContext(), attrs, 0);
    }

    public TransCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(getContext(), attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
        setBackgroundColor(Color.TRANSPARENT);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TransCircleView, defStyle,
                0);
        final int defSize = ViewUtils.getDisplayPerWidth(getContext(), DEFAULT_WIDTH_HEIGHT_PER);

        mDynamicHeight = a.getInteger(R.styleable.TransCircleView_dynamic_height, defSize);
        mDynamicWidth = a.getInteger(R.styleable.TransCircleView_dynamic_width, defSize);
        mImageResource = a.getResourceId(R.styleable.TransCircleView_dynamic_icon, R.drawable.ic_action_name);
        mDynamicTextSize = a.getDimension(R.styleable.TransCircleView_dynamic_textSize, DEFAULT_TEXT_SIZE);
        mDynamicText = a.getString(R.styleable.TransCircleView_dynamic_text);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = ViewUtils.getDisplayPerWidth(getContext(), mDynamicWidth);
        final int height = ViewUtils.getDisplayPerHeight(getContext(), mDynamicHeight);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(mBackGroundColor));
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

    public void setText(@NonNull String text) {
        this.mDynamicText = text;
        invalidate();
    }

    public void setIsLikeByBackgroundChanged(boolean isLike) {
        if(isLike) {
            mBackGroundColor = mLikeColor;
        } else {
            mBackGroundColor = mDefaultColor;
        }
        this.isLike = isLike;
        invalidate();
    }

    public boolean isLike() {
        return isLike;
    }
}
