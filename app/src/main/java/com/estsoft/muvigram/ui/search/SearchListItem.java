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
    private int mId;
    private String mTitle;


}
