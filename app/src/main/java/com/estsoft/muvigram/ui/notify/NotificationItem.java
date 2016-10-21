package com.estsoft.muvigram.ui.notify;

/**
 * Created by JEONGYI on 2016. 10. 21..
 */

public class NotificationItem {

    private NotifyFollowItem mNotifyFollowItem;
    private NotifyLikeItem mNotifyLikeItem;
    private NotifyReplyItem mNotifyReplyItem;

    public NotificationItem(){

    }

    public int getType(){
        if(mNotifyFollowItem!= null){
            return 0;
        }else if(mNotifyLikeItem != null){
            return 1;
        }else if(mNotifyReplyItem!= null){
            return 2;
        }

        return -1;
    }

    public void add(NotifyFollowItem item){
        this.mNotifyFollowItem = item;
    }

    public void add(NotifyLikeItem item){
        this.mNotifyLikeItem = item;
    }

    public void add(NotifyReplyItem item){
        this.mNotifyReplyItem = item;
    }

}
