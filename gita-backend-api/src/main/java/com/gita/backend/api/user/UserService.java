package com.gita.backend.api.user;

import com.gita.backend.dto.common.Response;
import com.gita.backend.dto.user.UserEnter;

/**
 * @Auther: yihang.lv
 * @Date: 2018/6/28 15:16
 * @Description: 用户服务
 */
public interface UserService {
    Response login(UserEnter enter);
    void logout();
}
