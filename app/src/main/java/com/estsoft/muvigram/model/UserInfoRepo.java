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
    private String password;
    private String email;
}
