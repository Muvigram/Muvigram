package com.estsoft.muvigram.ui.profile;

import android.os.Bundle;
import android.widget.EditText;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;

/**
 * Created by JEONGYI on 2016. 10. 14..
 */

public class EditProfileActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        String userId = getIntent().getStringExtra("userId");
        String bio = getIntent().getStringExtra("userBio");

        //프라이머리키로 객체 찾아서 아이디 바이오 set 해주기!

        EditText userIdEditText = (EditText)findViewById(R.id.userid_edittext);
        EditText bioEditText = (EditText)findViewById(R.id.userbio_edittext);

        userIdEditText.setText(userId);
        bioEditText.setText(bio);
    }
}
