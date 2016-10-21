package com.estsoft.muvigram.ui.search;

import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by gangGongUi on 2016. 10. 16..
 */

@Data
@AllArgsConstructor
@Accessors(prefix = "m")
public class SearchHeaderVideoItem {
    private Uri mVideoFile;
    private String mTitle;
}
