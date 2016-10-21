package com.estsoft.muvigram.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

    UserInfoRepo user = new UserInfoRepo("pwjddl1126", "박정이",
            "\"live with passion, live like muvigram\"",
            "https://scontent.xx.fbcdn.net/v/t1.0-9/12011354_171091463233969_4930354003965117617_n.jpg?oh=5d04533c62af8fed3eeab63f36df659a&oe=589FE419");
    //    @Inject
//    ProfilePresenter mProfilePresenter;
//    private Integer[] mProfileThum = mProfilePresenter.loadThumbnails();
    DisplayMetrics mMetrics;
    private Integer[] mProfileThum = {R.drawable.profile_test, R.drawable.profile_test1,
            R.drawable.profile_test2, R.drawable.profile_test3, R.drawable.profile_test};

    @OnClick(R.id.find_friend_button) void setFindFriendButton() {
        startActivity(new Intent(getActivity(), FindFriendActivity.class));
    }

    @OnClick(R.id.setting_button) void setSettingButton() {
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);

        ImageView profile = (ImageView) v.findViewById(R.id.profile_image);
        Picasso.with(getActivity())
                .load(user.getProfileImage())
                .transform(new CircleTransform()).into(profile);


        TextView userId = (TextView) v.findViewById(R.id.id);
        userId.setText("@" + user.getUserid());
        TextView userBio = (TextView) v.findViewById(R.id.bio);
        userBio.setText(user.getBio());

        //프로필 수정
        Button editProfileButton = (Button) v.findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            intent.putExtra("userId", user.getUserid());
            intent.putExtra("userBio", user.getBio());
            intent.putExtra("userName", user.getUserName());
            intent.putExtra("userProfileImage", user.getProfileImage());
            startActivity(intent);
        });

        //그리드뷰
        ExpandableHeightGridView gridView = (ExpandableHeightGridView) v.findViewById(R.id.profile_gridview);
        gridView.setExpanded(true);
        gridView.setAdapter(new ImageAdapter(getActivity()));
//        gridView.setOnItemClickListener(gridViewOnItemClickListener);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);

        return v;
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
                imageView.setLayoutParams(new GridView.LayoutParams(rowWidth, rowWidth));
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
