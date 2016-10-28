package com.estsoft.muvigram.model;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.FeedMenuButton;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.ui.feed.Holder;
import com.estsoft.muvigram.util.ViewUtils;
import com.estsoft.muvigram.util.transform.CircleTransform;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * Created by gangGongUi on 2016. 10. 24..
 */
public class FeedRepo implements ListItem {
    private static final int MAX_LOAD = 100;
    private static final int RUNNING_LOAD = 50;
    private final Rect mCurrentViewRect = new Rect();
    private final ItemCallback mItemCallback;
    private final Context mContext;
    private Uri mUri;
    private Drawable mThumbnail;
    private String mProfileimage;
    private int mPosition;

    public FeedRepo(Context mContext, Uri mUri, Drawable mThumbnail, String mProfileimage, ItemCallback mItemCallback) {
        this.mUri = mUri;
        this.mThumbnail = mThumbnail;
        this.mItemCallback = mItemCallback;
        this.mContext = mContext;
        this.mProfileimage = mProfileimage;
    }

    public void onBindViewHolder(Holder holder, int position) {


        // video set
        this.mPosition = position;
        holder.mView.setBackground(mThumbnail);
        final IncreasVideoView mIncreasVideoView = holder.mIncreasVideoView;
        if (mUri != null) {
            mIncreasVideoView.setVideoURI(mUri);
            mIncreasVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
            mIncreasVideoView.setSaveEnabled(true);
        }


        // menu set
        final FeedMenuButton mFeedMenuButton = holder.mFeedMenuButton;
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFeedMenuButton.getLayoutParams();
        final Display mDisplay = ViewUtils.getDisPlay(mContext);
        final int bottomMargin = mDisplay.getHeight() * 24 / 100;
        params.setMargins(0, 0, 0, bottomMargin);

        //

        Picasso.with(mContext)
                .load(mProfileimage)
                .transform(new CircleTransform())
                .into(holder.mDynamicImageView);

    }

    @Override public int getVisibilityPercents(View view) {

        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);

        //Timber.e("getVisibilityPercents mCurrentViewRect top " + mCurrentViewRect.top + ", left " + mCurrentViewRect.left + ", bottom " + mCurrentViewRect.bottom + ", right " + mCurrentViewRect.right);
        int height = view.getHeight();

        //Timber.e("getVisibilityPercents height " + height);

        if (viewIsPartiallyHiddenTop()) {
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        //Timber.e("<< getVisibilityPercents, percents " + percents);


        final IncreasVideoView mIncreasVideoView = ((IncreasVideoView) view.findViewById(R.id.videoview));
        final View placeHolder = view.findViewById(R.id.placeholder);

        if (percents == MAX_LOAD) {
            mIncreasVideoView.start();
            mIncreasVideoView.postDelayed(() -> placeHolder.setVisibility(View.GONE), 300);

        } else if (percents <= RUNNING_LOAD && placeHolder.getVisibility() != View.VISIBLE) {
            placeHolder.setVisibility(View.VISIBLE);
        }

        return percents;
    }

    @Override public void setActive(View newActiveView, int newActiveViewPosition) {
        mItemCallback.onActiveViewChangedActive(newActiveView, mPosition);
    }

    @Override public void deactivate(View currentView, int position) {
        currentView.findViewById(R.id.placeholder).setVisibility(View.VISIBLE);

    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }


    public interface ItemCallback {
        void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition);
    }
}
