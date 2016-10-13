package com.estsoft.muvigram.ui.sign;

import android.text.TextUtils;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.ConfigPersistent;
import com.estsoft.muvigram.ui.base.BasePresenter;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by gangGongUi on 2016. 10. 11..
 */
@ConfigPersistent
public class SignInPresenter extends BasePresenter<SignInView> {

    private final DataManager dataManager;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private final Pattern pattern = android.util.Patterns.EMAIL_ADDRESS;
    private Matcher matcher;

    @Inject
    public SignInPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(SignInView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeSubscription.unsubscribe();
    }


    public void attachSubscribe(Observable<CharSequence> emailChangeObservable, Observable<CharSequence> passwordChangeObservable) {

        Subscription emailSubscription = emailChangeObservable
                .doOnNext(charSequence -> getMvpView().hideEmailError())
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        boolean isEmailValid = validateEmail(charSequence.toString());
                        if (!isEmailValid) {
                            getMvpView().showEmailError();
                        } else {
                            getMvpView().hideEmailError();
                        }
                    }
                });

        compositeSubscription.add(emailSubscription);

        Subscription passwordSubscription = passwordChangeObservable
                .doOnNext(charSequence -> getMvpView().hidePasswordError())
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        boolean isPasswordValid = validatePassword(charSequence.toString());
                        if (!isPasswordValid) {
                            getMvpView().showPasswordError();
                        } else {
                            getMvpView().hidePasswordError();
                        }
                    }
                });

        compositeSubscription.add(passwordSubscription);

        Subscription signInFieldsSubscription = Observable.combineLatest(emailChangeObservable, passwordChangeObservable, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence email, CharSequence password) {
                boolean isEmailValid = validateEmail(email.toString());
                boolean isPasswordValid = validatePassword(password.toString());
                return isEmailValid && isPasswordValid;
            }
        }).observeOn(AndroidSchedulers.mainThread()) // UI Thread
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean validFields) {
                        if (validFields) {
                            getMvpView().enableSignIn();
                        } else {
                            getMvpView().disableSignIn();
                        }
                    }
                });

        compositeSubscription.add(signInFieldsSubscription);

    }


    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email))
            return false;
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        return password.length() > 5;
    }



}
