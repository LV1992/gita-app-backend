package com.gita.backend.dto.user;

import com.gita.backend.dto.common.GeneralEnter;
import lombok.Data;

/**
 * @author yihang.lv 2018/8/22、16:32
 */
@Data
public class UserEnter extends GeneralEnter {
    private String mobile;
    private String password;
}
