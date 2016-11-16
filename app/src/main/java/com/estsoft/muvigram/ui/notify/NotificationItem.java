package com.estsoft.muvigram.ui.notify;

import com.estsoft.muvigram.model.NotifyComment;
import com.estsoft.muvigram.model.NotifyFollow;
import com.estsoft.muvigram.model.NotifyLike;

import java.util.Comparator;

/**
 * Created by JEONGYI on 2016. 10. 21..
 */


public class NotificationItem {

    private NotifyFollowItem mNotifyFollowItem;
    private NotifyLikeItem mNotifyLikeItem;
    private NotifyReplyItem mNotifyReplyItem;

    private NotifyComment mNotifyComment;
    private NotifyFollow mNotifyFollow;
    private NotifyLike mNotifyLike;
    private long date;

    public NotificationItem(){

    }

    public int getType(){
        if(mNotifyFollow!= null){
            return 0;
        }else if(mNotifyLike != null){
            return 1;
        }else if(mNotifyComment!= null){
            return 2;
        }

        return -1;
    }

//    public void add(NotifyFollowItem item){
//        this.mNotifyFollowItem = item;
//    }
//
//    public void add(NotifyLikeItem item){
//        this.mNotifyLikeItem = item;
//    }
//
//    public void add(NotifyReplyItem item){
//        this.mNotifyReplyItem = item;
//    }


    public void add(NotifyComment item){
        this.mNotifyComment = item;
        date = item.date();
    }

    public void add(NotifyFollow item){
        this.mNotifyFollow = item;
        date = item.date();
    }

    public void add(NotifyLike item){
        this.mNotifyLike = item;
        date = item.date();
    }

    public NotifyComment getNotifyComment(){
        return mNotifyComment;
    }

    public NotifyFollow getNotifyFollow(){
        return mNotifyFollow;
    }

    public NotifyLike getNotifyLike(){
        return mNotifyLike;
    }
    public long getDate(){
        return date;
    }



}
