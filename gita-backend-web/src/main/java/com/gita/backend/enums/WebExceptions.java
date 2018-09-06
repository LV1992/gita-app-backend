package com.gita.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yihang.lv 2018/8/21、13:30
 */
@Getter
@AllArgsConstructor
public enum WebExceptions {

    BUILDING_QR_CODE_ERROR(00001,"生成二维码出错");

    private int code;
    private String msg;
}
