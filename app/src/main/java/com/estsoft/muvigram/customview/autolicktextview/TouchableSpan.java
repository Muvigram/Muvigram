package com.estsoft.muvigram.customview.autolicktextview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by Chatikyan on 26.09.2016-19:10.
 */

abstract class TouchableSpan extends ClickableSpan {

    private boolean isPressed;
    private int normalTextColor;
    private int pressedTextColor;
    private boolean isUnderLineEnabled;
    private Typeface mTypeface;

    TouchableSpan(int normalTextColor, int pressedTextColor, boolean isUnderLineEnabled, Typeface mTypeface) {
        this.normalTextColor = normalTextColor;
        this.pressedTextColor = pressedTextColor;
        this.isUnderLineEnabled = isUnderLineEnabled;
        this.mTypeface = mTypeface;
    }

    void setPressed(boolean isSelected) {
        isPressed = isSelected;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        int textColor = isPressed ? pressedTextColor : normalTextColor;
        textPaint.setColor(textColor);
        textPaint.bgColor = Color.TRANSPARENT;
        textPaint.setUnderlineText(isUnderLineEnabled);
        textPaint.setTypeface(mTypeface);
        textPaint.setAntiAlias(true);

    }
}