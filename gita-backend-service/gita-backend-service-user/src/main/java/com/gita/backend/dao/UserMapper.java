package com.gita.backend.dao;

import com.gita.backend.dto.user.UserEnter;
import com.gita.backend.model.UserModel;

/**
 * @author yihang.lv 2018/8/22、16:30
 */
public interface UserMapper {

    int selectUserByName(UserEnter enter);

    UserModel selectUser(UserEnter enter);
}
