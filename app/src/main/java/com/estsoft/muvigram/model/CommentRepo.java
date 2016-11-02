package com.estsoft.muvigram.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by gangGongUi on 2016. 11. 2..
 */
@Data
@AllArgsConstructor
public class CommentRepo {
    private String mProfileImageUri;
    private String mUsername;
    private String mText;
}
