package com.estsoft.muvigram.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.UserInfoRepo;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;
import com.estsoft.muvigram.ui.setting.SettingsActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.find_friend_button) ImageButton findFriendButton;
    @BindView(R.id.setting_button) ImageButton settingButton;
    @BindView(R.id.action_bar) RelativeLayout mActionBar;
    @BindView(R.id.id) TextView userId;
    @BindView(R.id.bio) TextView userBio;
    @BindView(R.id.name) TextView userName;

    UserInfoRepo user = new UserInfoRepo("pwjddl1126","박정이",
            "\"live with passion, live like muvigram\"",
            "https://pbs.twimg.com/media/CODCz6EUcAAvryE.jpg");
    private Integer[] mProfileThum = {R.drawable.profile_test, R.drawable.profile_test1,
            R.drawable.profile_test2, R.drawable.profile_test3, R.drawable.profile_test};

//    @Inject
//    ProfilePresenter mProfilePresenter;
//    private Integer[] mProfileThum = mProfilePresenter.loadThumbnails();
    DisplayMetrics mMetrics;


    @OnClick(R.id.find_friend_button) void setFindFriendButton(){
        startActivity(new Intent(getActivity(), FindFriendActivity.class));
    }

    @OnClick(R.id.setting_button) void setSettingButton(){
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,v);

        ImageView profile = (ImageView) v.findViewById(R.id.profile_image);
        Picasso.with(getActivity())
                .load(user.getProfileImage())
                .transform(new CircleTransform()).into(profile);

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mActionBar.getLayoutParams();
        params.setMargins(0, ((MuvigramApplication) getActivity().getApplication()).getStatusBarHeight(), 0, 0);
        mActionBar.setLayoutParams(params);

        userId.setText("@"+user.getUserid());
        userBio.setText(user.getBio());
        userName.setText(user.getUserName());

        //프로필 수정
        Button editProfileButton = (Button)v.findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            intent.putExtra("userId", user.getUserid());
            intent.putExtra("userBio", user.getBio());
            intent.putExtra("userName", user.getUserName());
            intent.putExtra("userProfileImage",user.getProfileImage());
            startActivityForResult(intent,0);
        });

        //그리드뷰
        ExpandableHeightGridView gridView = (ExpandableHeightGridView)v.findViewById(R.id.profile_gridview);
        gridView.setExpanded(true);
        gridView.setAdapter(new ImageAdapter(getActivity()));
//        gridView.setOnItemClickListener(gridViewOnItemClickListener);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -1:
                Log.d("----->","asdfasdf");
                String userId = data.getStringExtra("userId");
                String userName = data.getStringExtra("userName");
                String userBio = data.getStringExtra("userBio");
                user.setUserid(userId);
                user.setUserName(userName);
                user.setBio(userBio);
                break;
            default:
                break;
        }

        userId.setText("@"+user.getUserid());
        userBio.setText(user.getBio());
        userName.setText(user.getUserName());
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mProfileThum.length;
        }

        public Object getItem(int position) {
            return mProfileThum[position];
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            int rowWidth = (mMetrics.widthPixels) / 3;

            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(rowWidth,rowWidth));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(1, 1, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mProfileThum[position]);
            return imageView;
        }
    }


}
