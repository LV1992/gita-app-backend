package com.gita.backend.model;

import com.gita.backend.dto.common.GeneralEnter;
import lombok.Data;

/**消息模板
 * ${yihang.lv}、2018/6/28、15:50
 */
@Data
public class MessageModel extends GeneralEnter{
    private String title;
    private String msg;
    /**
     * 消息类型
     */
    private int msgType;
    /**
     * 发送消息目的的用户id
     */
    private int toUserId;
    /**
     * 是否群发
     */
    private boolean isSendAll;
}
