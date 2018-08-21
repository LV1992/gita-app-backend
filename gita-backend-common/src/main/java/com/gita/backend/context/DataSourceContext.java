package com.gita.backend.context;

/**
 * @author yihang.lv 2018/8/21、13:12
 */
public class DataSourceContext{

    private static final ThreadLocal<String> context = new ThreadLocal<String>();

    public static ThreadLocal get(){
        return context;
    }

    public static void set(String str){
        context.set(str);
    }

    public static String getDataSource(){
        return context.get();
    }

    public static void clear(){
        context.remove();
    }
}
