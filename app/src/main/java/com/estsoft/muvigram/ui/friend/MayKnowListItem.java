package com.estsoft.muvigram.ui.friend;

/**
 * Created by gangGongUi on 2016. 10. 16..
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(prefix = "m")
public class MayKnowListItem {
    private String mProfile;
    private String mName;
    private String mId;

    MayKnowListItem(String name, String id){
        mName = name;
        mId = id;
    }
}
