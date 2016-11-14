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
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.injection.component.ParentFragmentComponent;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.model.ProfileThumbnailRepo;
import com.estsoft.muvigram.model.UserInfoRepo;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.setting.SettingsActivity;
import com.estsoft.muvigram.util.DialogFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class ProfileFragment extends Fragment implements ProfileFragmentView{

    @Inject ProfileFragmentPresenter mPresenter;
    ImageAdapter mAdapter ;

    @BindView(R.id.find_friend_button) ImageButton findFriendButton;
    @BindView(R.id.setting_button) ImageButton settingButton;
    @BindView(R.id.action_bar) RelativeLayout mActionBar;
    @BindView(R.id.profile_image) ImageView profileImageView;
    @BindView(R.id.id) TextView userIdTextView;
    @BindView(R.id.bio) TextView userBioTextView;
    @BindView(R.id.name) TextView userNameTextView;
    @BindView(R.id.profile_gridview) ExpandableHeightGridView gridView;

    private String userId;
    private String userName;
    private String profileImage;
    private String bio;

    DisplayMetrics mMetrics;


    @OnClick(R.id.find_friend_button) void setFindFriendButton(){
        startActivity(new Intent(getActivity(), FindFriendActivity.class));
    }

    @OnClick(R.id.setting_button) void setSettingButton(){
        startActivity(new Intent(getActivity(), SettingsActivity.class));
    }

    @OnClick(R.id.edit_profile_button) void clickEditProfile(){
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userBio", bio);
        intent.putExtra("userName", userName);
        intent.putExtra("userProfileImage",profileImage);
        startActivityForResult(intent,0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ParentFragmentComponent activityComponent = ((HomeActivity) getActivity()).getSingleFragmentActivityComponent(this);

        activityComponent.inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,v);

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mActionBar.getLayoutParams();
        params.setMargins(0, ((MuvigramApplication) getActivity().getApplication()).getStatusBarHeight(), 0, 0);
        mActionBar.setLayoutParams(params);

        mPresenter.attachView(this);
        mPresenter.loadUserInfo();
        mPresenter.loadThumbnail();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showUserInfo(UserInfoRepo userInfo){

        userId = userInfo.userid();
        userName  =userInfo.userName();
        profileImage = userInfo.profileImage();
        bio = userInfo.bio();

        Picasso.with(getActivity())
                .load(profileImage)
                .transform(new CircleTransform()).into(profileImageView);
        profileImageView.setFocusable(true);

        userIdTextView.setText("@"+userId);
        userBioTextView.setText("\""+bio+"\"");
        userNameTextView.setText(userName);

    }

    @Override
    public void showUserInfoEmpty(){
        Toast.makeText(getContext(), "no userInfo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserInfoError(){
        DialogFactory.createGenericErrorDialog(getContext(),
                "There was an error loading UserInfo.").show();
    }

    @Override
    public void showThumbnail(List<ProfileThumbnailRepo> thumbnail){
        gridView.setExpanded(true);
        mAdapter = new ImageAdapter(getActivity());
        mAdapter.setThumbnails(thumbnail);
        gridView.setAdapter(mAdapter);

//        gridView.setOnItemClickListener(gridViewOnItemClickListener);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    @Override
    public void showThumbnailEmpty(){
        Toast.makeText(getContext(), "no thumbnail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThumbnailError(){
        DialogFactory.createGenericErrorDialog(getContext(),
                "There was an error loading Thumbnail.").show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case -1:
                Log.d("----->","asdfasdf");
                String tempUserId = data.getStringExtra("userId");
                String tempUserName = data.getStringExtra("userName");
                String tempUserBio = data.getStringExtra("userBio");

                //TODO-- insert editted info into database
//                user.setUserid(userId);
//                user.setUserName(userName);
//                user.setBio(userBio);
                userId = tempUserId;
                userName = tempUserName;
                bio = tempUserBio;
                break;

            default:
                break;
        }

        userIdTextView.setText("@"+userId);
        userBioTextView.setText("\""+bio+"\"");
        userNameTextView.setText(userName);
    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;
        private List<ProfileThumbnailRepo> mThumbnails;

        @ActivityContext
        public ImageAdapter(Context context) {
            mContext = context;
            this.mThumbnails = new ArrayList<>();
        }

        public void setThumbnails(List<ProfileThumbnailRepo> thumbnails){
            mThumbnails = thumbnails;
        }

        public int getCount() {
            return mThumbnails.size();
        }

        public Object getItem(int position) {
            return mThumbnails.get(position);
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

            Picasso.with(mContext)
                    .load(mThumbnails.get(position).thumbnail())
                    .into(imageView);

            return imageView;
        }
    }


}
