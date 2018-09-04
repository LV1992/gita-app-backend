package com.gita.backend.configuration.dbProperties.aop;

import com.gita.backend.annotation.DataSource;
import com.gita.backend.context.DataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: yihang.lv
 * @Date: 2018/7/4 14:56
 * @Description: 数据源切换处理
 */
@Slf4j
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("execution(* com.gita.backend.*.mapper..*.*(..))")
    public void changeDataSource() {
    }

    /**
     * 在调用方法之前切换数据源
     * @param joinPoint
     */
    @Before("changeDataSource()")
    public void before(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DataSource annotation = method.getAnnotation(DataSource.class);
        if (annotation == null) {
            return;
        }
        //通过注解来拿数据源，设置到threadlocal中
        DataSourceContext.set(annotation.value().getName());
    }

    /**
     * 清除设置到threadlocal的数据源
     */
    @After("changeDataSource()")
    public void after() {
        DataSourceContext.clear();
    }

}
