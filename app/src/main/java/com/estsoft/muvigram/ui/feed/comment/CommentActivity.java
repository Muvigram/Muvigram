package com.estsoft.muvigram.ui.feed.comment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.CommentRepo;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.util.DisplayUtility;
import com.jakewharton.rxbinding.view.RxView;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class CommentActivity extends BasePlainActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.cancelButton) ImageButton mCancleButton;
    @BindView(R.id.titleTv) TextView mTitleView;
    @BindView(R.id.relative_layout) RelativeLayout mRelativeLayout;
    @BindView(R.id.comment_edit_text) EditText mCommentEditText;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private CommentAdapter mCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlainActivityComponent().inject(this);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        initView();

        RxView.clicks(mCancleButton)
                .subscribe(aVoid -> finish());

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRelativeLayout.getLayoutParams();
        params.setMargins(0, ((MuvigramApplication) getApplication()).getStatusBarHeight(), 0, 0);
        mRelativeLayout.setLayoutParams(params);




        mCommentEditText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                final Subscription mDr_maggie = Observable.just(mCommentEditText.getText().toString())
                        .filter(text -> !text.isEmpty())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                text -> mCommentAdapter.addItem(
                                        new CommentRepo("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/160511_Park_Bo-young.jpg/250px-160511_Park_Bo-young.jpg", "dr_maggie", text)),
                                (err) -> Timber.e(err.getMessage()),
                                () -> { // completed
                                    mRecyclerView.scrollToPosition(0);
                                    mCommentEditText.setText("");
                                    DisplayUtility.hideKeyboard(getBaseContext(), mCommentEditText);
                                });
                compositeSubscription.add(mDr_maggie);
                return false;
            }
            return true;
        });

    }


    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mCommentAdapter = new CommentAdapter(getDummyCommentReopos(5), getBaseContext());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mCommentAdapter);
    }

    @Override public void onRefresh() {
        final Subscription mSubscribe = Observable.from(getDummyCommentReopos(2))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(() -> {
                    mRecyclerView.scrollToPosition(0);
                    mSwipeRefreshLayout.setRefreshing(false);
                })
                .subscribe(mCommentAdapter::addItem);
        compositeSubscription.add(mSubscribe);
    }

    private LinkedList<CommentRepo> getDummyCommentReopos(int n) {
        LinkedList<CommentRepo> ret = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            ret.add(
                    new CommentRepo("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/160511_Park_Bo-young.jpg/250px-160511_Park_Bo-young.jpg",
                            "dr_maggie",
                            "wooooooooooooo happyhelloween muahahahahahahahahahahahahahahaha")
            );
        }
        return ret;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
