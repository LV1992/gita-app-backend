package com.gita.backend.dto.entity;

import com.gita.backend.dto.common.GeneralEnter;
import lombok.Data;

import java.util.concurrent.CountDownLatch;

/**登录用户模型
 * @author yihang.lv 2018/9/6、15:45
 */
@Data
public class LoginSession extends GeneralEnter{
    private Long id;
    private String mobile;
    /**
     * 用于同步的线程信号量，当信号量的值为0时，阻塞的线程（object.wait（）后的代码）可以执行
     */
    public CountDownLatch latch;
}
