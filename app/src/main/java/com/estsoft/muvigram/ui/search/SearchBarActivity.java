package com.estsoft.muvigram.ui.search;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TabHost;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchBarActivity extends TabActivity {

    @BindView(R.id.search_edittext)
    EditText searchEditText;

    private SearchItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
        ButterKnife.bind(this);

        TabHost mTab = getTabHost();
        TabHost.TabSpec spec;

        spec = mTab.newTabSpec("Tab1").setIndicator("people").setContent(new Intent(getApplicationContext(), SearchPeopleActivity.class));
        mTab.addTab(spec);
        spec = mTab.newTabSpec("Tab2").setIndicator("tags").setContent(new Intent(getApplicationContext(), SearchTagActivity.class));
        mTab.addTab(spec);
        spec = mTab.newTabSpec("Tab3").setIndicator("sounds").setContent(new Intent(getApplicationContext(), SearchSoundActivity.class));
        mTab.addTab(spec);

        getTabHost().getTabWidget().getChildAt(0).setOnClickListener(v -> {
            searchEditText.setHint(getString(R.string.search_people));
        });

        getTabHost().getTabWidget().getChildAt(1).setOnClickListener(v -> {
            searchEditText.setHint(getString(R.string.search_tags));
        });

        getTabHost().getTabWidget().getChildAt(2).setOnClickListener(v -> {
            searchEditText.setHint(getString(R.string.search_sounds));
        });


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = searchEditText.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
                //여기서 어케 리싸이클러뷰에 붙이지 ;ㅅ;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
