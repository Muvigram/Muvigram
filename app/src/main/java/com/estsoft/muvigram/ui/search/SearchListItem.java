package com.estsoft.muvigram.ui.search;

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
public class SearchListItem {
    private String mProfile;
    private String mTitle;
    private String mSubTitle;

    SearchListItem(String title, String subTitle){
        mTitle = title;
        mSubTitle = subTitle;
    }
}
