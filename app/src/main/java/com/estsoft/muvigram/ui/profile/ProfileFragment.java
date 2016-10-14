package com.estsoft.muvigram.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.UserInfoRepo;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.setting.SettingsActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class ProfileFragment extends Fragment {

//    @BindView(R.id.profile_image)
//    ImageView profile;
    UserInfoRepo user = new UserInfoRepo("pwjddl1126","박정이","나는 나다 시바!");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setCustomActionbar();

        ImageView profile = (ImageView) v.findViewById(R.id.profile_image);
        Picasso.with(getActivity())
                .load("https://scontent.xx.fbcdn.net/v/t1.0-9/12011354_171091463233969_4930354003965117617_n.jpg?oh=5d04533c62af8fed3eeab63f36df659a&oe=589FE419")
                .transform(new CircleTransform()).into(profile);


        TextView userId = (TextView)v.findViewById(R.id.id);
        userId.setText(user.getUserid());
        TextView userBio = (TextView)v.findViewById(R.id.bio);
        userBio.setText(user.getBio());


        Button editProfileButton = (Button)v.findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            intent.putExtra("userId", user.getUserid());
            intent.putExtra("userBio", user.getBio());
            startActivity(intent);
        });

        return v;
    }

    private void setCustomActionbar(){
        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
        actionBar.show();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View mCustomView = LayoutInflater.from(getActivity()).inflate(R.layout.actionbar_profile, null);

        mCustomView.findViewById(R.id.find_friend_button).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), FindFriendActivity.class));
        });

        mCustomView.findViewById(R.id.setting_button).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        });

        actionBar.setCustomView(mCustomView);
    }

}
