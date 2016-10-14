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
import com.estsoft.muvigram.ui.base.BaseActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.util.DisplayUtility;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;


public class SignInActivity extends BaseActivity implements SignInView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.email_til) TextInputLayout emailInputLayout;
    @BindView(R.id.password_til) TextInputLayout passwordInputLayout;
    @BindView(R.id.password_et) EditText passwordEditText;
    @BindView(R.id.email_et) EditText emailEditText;
    @BindView(R.id.sign_in_ll) LinearLayout signInLinearLayout;
    @BindView(R.id.sign_in_btn) Button signInButton;

    @Inject
    SignInPresenter mSignInPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        mSignInPresenter.attachView(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.sign_in));
        }

        Observable<CharSequence> emailChangeObservable = RxTextView.textChanges(emailEditText);
        Observable<CharSequence> passwordChangeObservable = RxTextView.textChanges(passwordEditText);
        mSignInPresenter.attachSubscribe(emailChangeObservable, passwordChangeObservable);

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
    public void showEmailError(){
        enableError(emailInputLayout);
        // emailInputLayout.setErrorEnabled(true);
        emailInputLayout.setError(getString(R.string.invalid_email));
    }

    @Override
    public void hideEmailError(){
        disableError(emailInputLayout);
        // emailInputLayout.setErrorEnabled(false);
        emailInputLayout.setError(null);
    }

    @Override
    public void showPasswordError(){
        enableError(passwordInputLayout);
        // passwordInputLayout.setErrorEnabled(true);
        passwordInputLayout.setError(getString(R.string.invalid_password));
    }

    @Override
    public void hidePasswordError(){
        disableError(passwordInputLayout);
        // passwordInputLayout.setErrorEnabled(false);
        passwordInputLayout.setError(null);
    }

    @Override
    public void enableSignIn(){
        signInLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        signInButton.setEnabled(true);
        signInButton.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    }

    @Override
    public void disableSignIn(){
        signInLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_400));
        signInButton.setEnabled(false);
        signInButton.setTextColor(ContextCompat.getColor(this, R.color.grey_500));
    }


}

