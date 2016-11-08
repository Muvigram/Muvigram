package com.estsoft.muvigram.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.util.sounds.CheapSoundFile;

/**
 * Created by estsoft on 2016-11-01.
 */

public class WaveformView extends View {

    private static final String TAG = "MyWaveformView";

    private CheapSoundFile mSoundFile;
    private int mSampleRate;
    private int mSamplesPerFrame;

    private float scaleFactor;
    private float minGain;
    private float range;
    private int durationSec;
    private int fullWaveWidth;
    private int viewWidth;
    private float ratio;

    private boolean mInitialized;

    private int playingPositionPixel = 0;

    private int mOffset;

//    private final int STANDARD_DURATION = 15;
    private int mStandardDurationMs;

    private Paint normalPaint;
    private Paint playedPaint;

    private boolean isDrawing;

    public WaveformView(Context context) {
        super(context);
    }

    public WaveformView(Context context, AttributeSet attrs) {
        super(context, attrs);
        normalPaint = new Paint();
        normalPaint.setAntiAlias(false);
        normalPaint.setColor(getResources().getColor(R.color.waveNormal));
        normalPaint.setAlpha(50);
        normalPaint.setStrokeWidth(3);
        normalPaint.setStrokeCap(Paint.Cap.ROUND);

        playedPaint = new Paint();
        playedPaint.setAntiAlias(false);
        playedPaint.setColor(getResources().getColor(R.color.wavePlayed));
        playedPaint.setStrokeWidth(3);
        playedPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    public void init(CheapSoundFile soundFile, int standardDuration) {
        mStandardDurationMs = standardDuration;
        mSoundFile = soundFile;
        mSampleRate = mSoundFile.getSampleRate();
        mSamplesPerFrame = mSoundFile.getSamplesPerFrame();
        Log.d(TAG, "setSoundFile: FrameGain Length ... " + mSoundFile.getFrameGains().length + " / FrameNum ... " + mSoundFile.getNumFrames());
        computeDoublesForAllZoomLevels();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isDrawing == true) return;
        isDrawing = true;

        if (mSoundFile == null) {
            return;
        }

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int width = measuredWidth;
        int ctr = measuredHeight / 2;

        int i = 0;
        while (i < width) {
            drawWaveform(canvas, i, mOffset, ctr, selectPaint( i + mOffset ));
            i++;
        }

        isDrawing = false;

    }

    public void setParameters( int playedPositionMs, int newOffsetPixel ) {
        playingPositionPixel = millisecondToPixel( playedPositionMs );
        mOffset = newOffsetPixel;
    }

    public int millisecondToPixel(int ms ) {
        return (int) ((ms * 1.0 * mSampleRate * ratio) / (1000.0 * mSamplesPerFrame) + 0.5);
    }
    public int pixelToMillisecond(int pixel ) {
        return (int) (pixel * (1000.0 * mSamplesPerFrame) / (mSampleRate * ratio) + 0.5);
    }

    public Paint selectPaint( int position ) {
        if ( position <= playingPositionPixel ) return playedPaint;
        else return normalPaint;
    }

    public void computeDoublesForAllZoomLevels() {
        int numFrames = mSoundFile.getNumFrames();

        // Make sure the range is no more than 0 - 255
        float maxGain = 1.0f;
        for (int i = 0; i < numFrames; i++) {
            float gain = getGain(i, numFrames, mSoundFile.getFrameGains());
            if (gain > maxGain) {
                maxGain = gain;
            }
        }
        scaleFactor = 1.0f;
        if (maxGain > 255.0) {
            scaleFactor = 255 / maxGain;
        }

        // Build histogram of 256 bins and figure out the new scaled max
        maxGain = 0;
        int gainHist[] = new int[256];
        for (int i = 0; i < numFrames; i++) {
            int smoothedGain = (int) (getGain(i, numFrames, mSoundFile.getFrameGains()) * scaleFactor);
            if (smoothedGain < 0)
                smoothedGain = 0;
            if (smoothedGain > 255)
                smoothedGain = 255;

            if (smoothedGain > maxGain)
                maxGain = smoothedGain;

            gainHist[smoothedGain]++;
        }

        // Re-calibrate the min to be 5%
        minGain = 0;
        int sum = 0;
        while (minGain < 255 && sum < numFrames / 20) {
            sum += gainHist[(int) minGain];
            minGain++;
        }

        // Re-calibrate the max to be 99%
        sum = 0;
        while (maxGain > 2 && sum < numFrames / 100) {
            sum += gainHist[(int) maxGain];
            maxGain--;
        }

        range = maxGain - minGain;

        durationSec =  (mSoundFile.getSamplesPerFrame() * mSoundFile.getNumFrames()) / mSoundFile.getSampleRate();
        Log.d(TAG, "computeDoublesForAllZoomLevels: duration sec ... " + durationSec);
        viewWidth = getMeasuredWidth();
        Log.d(TAG, "computeDoublesForAllZoomLevels: " + (mStandardDurationMs / 1000));
        int standardDurationSec = mStandardDurationMs / 1000;
        fullWaveWidth = (int)((((float)durationSec / standardDurationSec)) * viewWidth);
        Log.d(TAG, "computeDoublesForAllZoomLevels: ... " + viewWidth + " / " + numFrames);
        ratio = fullWaveWidth / (float)numFrames;

        mInitialized = true;

        Log.d(TAG, "computeDoublesForAllZoomLevels:  End of this method + ratio is " + ratio);

    }

    private void drawWaveform(final Canvas canvas, final int i, final int start, final int ctr, final Paint paint) {

        double startRatio = (double)start / fullWaveWidth;
        int gainPosition = (int)(mSoundFile.getNumFrames() * startRatio);
        int gainIndex = (int)((float)i / ratio);

        int h = (int) (getScaledHeight(1, gainPosition + gainIndex) * getMeasuredHeight() / 2);
        canvas.drawLine(i, ctr - h, i, ctr + 1 + h, paint);
    }
    private float getScaledHeight( float zoomLevel, int i) {
        if (zoomLevel == 1.0) {
            return getNormalHeight(i);
        } else if (zoomLevel < 1.0) {
            return getZoomedOutHeight(zoomLevel, i);
        }
        return getZoomedInHeight(zoomLevel, i);
    }
    protected float getZoomedInHeight(float zoomLevel, int i) {
        int f = (int) zoomLevel;
        if (i == 0) {
            return 0.5f * getHeight(0, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
        }
        if (i == 1) {
            return getHeight(0, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
        }
        if (i % f == 0) {
            float x1 = getHeight(i / f - 1, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
            float x2 = getHeight(i / f, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
            return 0.5f * (x1 + x2);
        } else if ((i - 1) % f == 0) {
            return getHeight((i - 1) / f, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
        }
        return 0;
    }
    protected float getZoomedOutHeight(float zoomLevel, int i) {
        int f = (int) (i / zoomLevel);
        float x1 = getHeight(f, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
        float x2 = getHeight(f + 1, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
        return 0.5f * (x1 + x2);
    }
    protected float getNormalHeight(int i) {
        return getHeight(i, mSoundFile.getNumFrames(), mSoundFile.getFrameGains(), scaleFactor, minGain, range);
    }
    protected float getHeight(int i, int numFrames, int[] frameGains, float scaleFactor, float minGain, float range) {
        float value = (getGain(i, numFrames, frameGains) * scaleFactor - minGain) / range;
        if (value < 0.0)
            value = 0.0f;
        if (value > 1.0)
            value = 1.0f;
        return value;
    }
    protected float getGain(int i, int numFrames, int[] frameGains) {
        int x = Math.min(i, numFrames - 1);
        if (numFrames < 2) {
            return frameGains[x];
        } else {
            if (x == 0) {
                return (frameGains[0] / 2.0f) + (frameGains[1] / 2.0f);
            } else if (x == numFrames - 1) {
                return (frameGains[numFrames - 2] / 2.0f) + (frameGains[numFrames - 1] / 2.0f);
            } else {
                return (frameGains[x - 1] / 3.0f) + (frameGains[x] / 3.0f) + (frameGains[x + 1] / 3.0f);
            }
        }
    }



}
