package com.estsoft.muvigram.ui.search;

import android.net.Uri;

/**
 * Created by gangGongUi on 2016. 10. 16..
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(prefix = "m")
public class SearchHeaderVideoItem {
    private Uri mVideoFile;
    private String mTitle;
}
