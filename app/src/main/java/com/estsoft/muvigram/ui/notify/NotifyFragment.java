package com.estsoft.muvigram.ui.notify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class NotifyFragment extends Fragment {

    Fragment fragment = null;
    NotifyAllFragment notifyAllFragment = null;
    NotifyPreferFragment notifyPreferFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_notify, container, false);


        FragmentManager fm = getChildFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container_notify);

        if(fragment == null) {
//            Toast.makeText(getActivity(),"null!!!!!",Toast.LENGTH_SHORT).show();
            notifyAllFragment = new NotifyAllFragment();
            fragment = notifyAllFragment;
            fm.beginTransaction().replace(R.id.container_notify, fragment).commit();
        }

        return v;
    }

//    private void setCustomActionbar(){
//        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
//        actionBar.show();
//
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//
//        View mCustomView = LayoutInflater.from(getActivity()).inflate(R.layout.actionbar_notify, null);
//
//        FragmentManager fm = getChildFragmentManager();
//
//        mCustomView.findViewById(R.id.all_button).setOnClickListener(v -> {
////            Fragment fragment = fm.findFragmentById(R.id.container_notify);
//            fragment = notifyAllFragment;
//            fm.beginTransaction().replace(R.id.container_notify, fragment).commit();
//        });
//
//        mCustomView.findViewById(R.id.prefer_button).setOnClickListener(v ->{
////            Fragment fragment = fm.findFragmentById(R.id.container_notify);
//            if(notifyPreferFragment == null) {
//                notifyPreferFragment = new NotifyPreferFragment();
//            }
//            fragment = notifyPreferFragment;
//            fm.beginTransaction().replace(R.id.container_notify, fragment).commit();
//        });
//
//
//        actionBar.setCustomView(mCustomView);
//    }

}
