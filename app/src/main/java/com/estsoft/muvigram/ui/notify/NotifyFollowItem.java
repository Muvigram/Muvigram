package com.estsoft.muvigram.ui.notify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by JEONGYI on 2016. 10. 19..
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(prefix = "m")
public class NotifyFollowItem {

    private int mId;
    private String mTitle;

}