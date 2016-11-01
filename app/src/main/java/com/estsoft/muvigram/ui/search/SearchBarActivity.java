package com.estsoft.muvigram.ui.search;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchBarActivity extends BaseActivity{

    @BindView(R.id.search_edittext)
    EditText searchEditText;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.people_tab_clicked_line)
    RelativeLayout peopleTabClickedLine;
    @BindView(R.id.tags_tab_clicked_line) RelativeLayout tagsTabClickedLine;
    @BindView(R.id.sounds_tab_clicked_line) RelativeLayout soundsTabClickedLine;

    private SearchItemAdapter adapter;
    private final static int PEOPLE_INDEX = 0;
    private final static int TAGS_INDEX = 1;
    private final static int SOUNDS_INDEX = 2;

    @OnClick(R.id.people_tab) void peopleTabClicked(){
        searchEditText.setHint(getString(R.string.search_people));
        peopleTabClickedLine.setBackgroundResource(R.color.light_blue_300);
        tagsTabClickedLine.setBackgroundResource(R.color.grey_200);
        soundsTabClickedLine.setBackgroundResource(R.color.grey_200);

        setRecyclerView(PEOPLE_INDEX);
        searchEditTextWatcher();
    }

    @OnClick(R.id.tags_tab) void tagsTabClicked(){
        searchEditText.setHint(getString(R.string.search_tags));
        peopleTabClickedLine.setBackgroundResource(R.color.grey_200);
        tagsTabClickedLine.setBackgroundResource(R.color.light_blue_300);
        soundsTabClickedLine.setBackgroundResource(R.color.grey_200);

        setRecyclerView(TAGS_INDEX);
        searchEditTextWatcher();
    }

    @OnClick(R.id.sounds_tab) void soundsTabClicked(){
        searchEditText.setHint(getString(R.string.search_sounds));
        peopleTabClickedLine.setBackgroundResource(R.color.grey_200);
        tagsTabClickedLine.setBackgroundResource(R.color.grey_200);
        soundsTabClickedLine.setBackgroundResource(R.color.light_blue_300);

        setRecyclerView(SOUNDS_INDEX);
        searchEditTextWatcher();
    }

    @OnClick(R.id.cancel_textview) void clickCancel(){
        searchEditText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
        ButterKnife.bind(this);

        peopleTabClicked();

    }

    public void searchEditTextWatcher(){
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = searchEditText.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = searchEditText.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchEditText.getText().toString()
                        .toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
    }

    public void setRecyclerView(int index){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        if(index == PEOPLE_INDEX) {
            adapter = new SearchItemAdapter(getSearchPeopleItems(), PEOPLE_INDEX, getApplicationContext());
        }else if(index == TAGS_INDEX){
            adapter = new SearchItemAdapter(getSearchTagItems(), TAGS_INDEX, getApplicationContext());
        }else if(index == SOUNDS_INDEX){
            adapter = new SearchItemAdapter(getSearchSoundItems(), SOUNDS_INDEX, getApplicationContext());
        }

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        params.setMargins(0,0,0,0);
        recyclerView.setLayoutParams(params);
    }


    //가짜데이터
    public List<SearchListItem> getSearchPeopleItems()
    {
        List<SearchListItem> listItems = new ArrayList<>();
        SearchListItem[] items = new SearchListItem[6];

        for(int i=0; i<items.length; i++){
            items[i] = new SearchListItem("name#"+i,"context context context");
            listItems.add(items[i]);
        }

        return listItems;
    }

    public List<SearchListItem> getSearchTagItems()
    {
        List<SearchListItem> listItems = new ArrayList<>();
        SearchListItem[] items = new SearchListItem[5];

        final String[] titleList = {"comedy","question","dance","snapchatstory","muservoice"};
        final int[] subTitleList = {123,34,436,567,12};

        for(int i=0; i<items.length; i++){
            items[i] = new SearchListItem("#"+titleList[i],subTitleList[i]+ " muvigrams");
            listItems.add(items[i]);
        }

        return listItems;
    }

    public List<SearchListItem> getSearchSoundItems()
    {
        List<SearchListItem> listItems = new ArrayList<>();
        SearchListItem[] items = new SearchListItem[3];

        final String[] titleList = {"Closer ft Halsey","Cold Water","Perfect Illusion"};
        final String[] subTitleList = {"The Chainsomkers","Major Lazer","Lady Gaga"};

        for(int i=0; i<items.length; i++){
            items[i] = new SearchListItem(titleList[i],subTitleList[i]);
            listItems.add(items[i]);
        }

        return listItems;
    }

}
