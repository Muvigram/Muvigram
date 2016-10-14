package com.estsoft.muvigram.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by gangGongUi on 2016. 10. 13..
 */
@Data
@AllArgsConstructor
public class UserInfoRepo {
    private String userid;
    private String userName;
    private String password;
    private String email;
    private String bio;

    public UserInfoRepo(String userid, String userName, String bio){
        this.userid = userid;
        this.userName = userName;
        this.bio = bio;
    }
}
