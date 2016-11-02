package com.estsoft.muvigram.ui.sign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.intro.IntroActivity;
import com.estsoft.muvigram.util.DisplayUtility;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;


public class SignInActivity extends BasePlainActivity implements SignInView {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.username_til) TextInputLayout mUsernameInputLayout;
    @BindView(R.id.email_til) TextInputLayout mEmailInputLayout;
    @BindView(R.id.password_til) TextInputLayout mPasswordInputLayout;
    @BindView(R.id.username_et) EditText mUserNameEditText;
    @BindView(R.id.password_et) EditText mPasswordEditText;
    @BindView(R.id.email_et) EditText mEmailEditText;
    @BindView(R.id.sign_in_ll) LinearLayout mSignInLinearLayout;
    @BindView(R.id.sign_in_btn) Button mSignInButton;

    @Inject
    SignInPresenter mSignInPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlainActivityComponent().inject(this);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        mSignInPresenter.attachView(this);

        Observable<CharSequence> emailChangeObservable = RxTextView.textChanges(mEmailEditText);
        Observable<CharSequence> passwordChangeObservable = RxTextView.textChanges(mPasswordEditText);
        Observable<CharSequence> userIdChangeObservable = RxTextView.textChanges(mUserNameEditText);


        // 로그인인지 회원가입인지 확인여부
        int flags = getIntent().getIntExtra(IntroActivity.KEY, 0);
        actionBarInit(flags);

        if (flags == IntroActivity.LOG_IN_ACTIVITY) {
            mUserNameEditText.setVisibility(View.GONE);
            mUsernameInputLayout.setVisibility(View.GONE);
            userIdChangeObservable = null;
        } else {
            mSignInButton.setText(getString(R.string.sign_up));
        }

        mSignInPresenter.attachSubscribe(emailChangeObservable, passwordChangeObservable, userIdChangeObservable);

    }

    private void actionBarInit(int flags) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            String title = getString(R.string.log_in);
            if (flags == IntroActivity.SIGN_UP_ACTIVITY) {
                title = getString(R.string.sign_up);
            }
            actionBar.setTitle(title);
        }
    }


    @OnClick(R.id.sign_in_btn)
    public void onSignInButtonClicked(View view) {
        DisplayUtility.hideKeyboard(this, view);
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignInPresenter.detachView();
    }


    private void enableError(TextInputLayout textInputLayout) {
        if (textInputLayout.getChildCount() == 2)
            textInputLayout.getChildAt(1).setVisibility(View.VISIBLE);
    }

    private void disableError(TextInputLayout textInputLayout) {
        if (textInputLayout.getChildCount() == 2)
            textInputLayout.getChildAt(1).setVisibility(View.GONE);
    }

    @Override
    public void showEmailError() {
        enableError(mEmailInputLayout);
        mEmailInputLayout.setErrorEnabled(true);
        mEmailInputLayout.setError(getString(R.string.invalid_email));
    }

    @Override
    public void hideEmailError() {
        disableError(mEmailInputLayout);
        mEmailInputLayout.setErrorEnabled(false);
        mEmailInputLayout.setError(null);
    }

    @Override
    public void showPasswordError() {
        enableError(mPasswordInputLayout);
        mPasswordInputLayout.setErrorEnabled(true);
        mPasswordInputLayout.setError(getString(R.string.invalid_password));
    }

    @Override
    public void hidePasswordError() {
        disableError(mPasswordInputLayout);
        mPasswordInputLayout.setErrorEnabled(false);
        mPasswordInputLayout.setError(null);
    }

    @Override
    public void showUseridError() {
        disableError(mUsernameInputLayout);
        mUsernameInputLayout.setErrorEnabled(true);
        mUsernameInputLayout.setError(getString(R.string.invalid_userid));
    }

    @Override
    public void hideUseridError() {
        disableError(mUsernameInputLayout);
        mUsernameInputLayout.setErrorEnabled(false);
        mUsernameInputLayout.setError(null);
    }

    @Override
    public void enableSignIn() {
        mSignInLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        mSignInButton.setEnabled(true);
        mSignInButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    }

    @Override
    public void disableSignIn() {
        mSignInLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_400));
        mSignInButton.setEnabled(false);
        mSignInButton.setTextColor(ContextCompat.getColor(this, R.color.grey_500));
    }


}