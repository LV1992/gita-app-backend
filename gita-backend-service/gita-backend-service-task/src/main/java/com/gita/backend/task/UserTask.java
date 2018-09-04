package com.gita.backend.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yihang.lv 2018/9/4、14:57
 */
@JobHander(value = "userTask")
@Component
@Slf4j
public class UserTask extends IJobHandler{
    @Override
    public ReturnT<String> execute(String... strings) throws Exception {
        System.out.println("执行了 UserTask");
        return ReturnT.SUCCESS;
    }
}
