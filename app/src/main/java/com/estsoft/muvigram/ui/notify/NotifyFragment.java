package com.estsoft.muvigram.ui.notify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.estsoft.muvigram.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class NotifyFragment extends Fragment {

    static int clickedButton = 0;
    NotifyAllFragment notifyAllFragment = null;
    NotifyPreferFragment notifyPreferFragment = null;
    WebView mWebView;
    FragmentManager fm;

    @BindView(R.id.all_button) Button allButton;
    @BindView(R.id.prefer_button) Button preferButton;


    @OnClick(R.id.all_button) void setAllButton() {
        fm.beginTransaction().replace(R.id.container_notify, notifyAllFragment).commit();
        allButton.setBackgroundResource(R.color.white);
        preferButton.setBackgroundResource(R.color.gray);
        clickedButton = 0;
    }

    @OnClick(R.id.prefer_button) void setPreferButton() {
        if (notifyPreferFragment == null) {
            notifyPreferFragment = new NotifyPreferFragment();
        }
        fm.beginTransaction().replace(R.id.container_notify, notifyPreferFragment).commit();
        allButton.setBackgroundResource(R.color.gray);
        preferButton.setBackgroundResource(R.color.white);
        clickedButton = 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        ButterKnife.bind(this, view);

        fm = getChildFragmentManager();

        if (clickedButton == 0) {
            if (notifyAllFragment == null) {
                notifyAllFragment = new NotifyAllFragment();
            }
            fm.beginTransaction().replace(R.id.container_notify, notifyAllFragment).commit();
            allButton.setBackgroundResource(R.color.white);
        } else {
            fm.beginTransaction().replace(R.id.container_notify, notifyPreferFragment).commit();
            preferButton.setBackgroundResource(R.color.white);
        }


        return view;
    }


}
