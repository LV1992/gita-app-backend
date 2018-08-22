package com.gita.backend.exceptions;

import lombok.Data;

/**
 * @author yihang.lv 2018/8/22、17:12
 */
@Data
public class BusinessException extends RuntimeException{
    private String errorCode = "-1";
    private String errorMessage = "未知错误";
    /**
     * 可替换变量,相当于占位符
     */
    private String[] variables = null;

    public BusinessException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 使用错误码定义BusinessException
     *
     * @param errorCode 错误码
     */
    public BusinessException(int errorCode) {
        super(String.valueOf(errorCode), null);
    }

    /**
     * 使用错误码、提示信息的变量定义BusinessException
     *
     * @param errorCode 错误码
     * @param variables 用于在提示信息中展示的参数
     */
    public BusinessException(int errorCode, String[] variables) {
        super(String.valueOf(errorCode), null);
        this.variables = (variables == null ? null : variables.clone());
    }

    /**
     * 使用错误码、默认提示定义BusinessException
     *
     * @param errorCode    错误码
     * @param errorMessage 默认错误信息
     */
    public BusinessException(int errorCode, String errorMessage) {
        this.errorCode = String.valueOf(errorCode);
        this.errorMessage = errorMessage;
    }

    /**
     * 使用错误码、默认提示、提示信息的变量定义BusinessException
     *
     * @param errorCode    错误码
     * @param errorMessage 默认错误信息
     * @param variables    用于在提示信息中展示的参数
     */
    public BusinessException(int errorCode, String errorMessage, String[] variables) {
        this.errorCode = String.valueOf(errorCode);
        this.errorMessage = errorMessage;
        this.variables = (variables == null ? null : variables.clone());
    }

    /**
     * 将异常封装为BusinessException，并指定错误码
     *
     * @param errorCode 错误码
     * @param t         源异常
     */
    public BusinessException(int errorCode, Throwable t) {
        super(String.valueOf(errorCode), t);
    }

    /**
     * 将异常封装为BusinessException，并指定错误码和默认提示
     *
     * @param errorCode    错误码
     * @param errorMessage 错误描述
     * @param t            源异常
     */
    public BusinessException(int errorCode, String errorMessage, Throwable t) {
        super(errorMessage,t);
        this.errorCode = String.valueOf(errorCode);
    }


    /**
     * 异常堆栈增加错误代码和绑定变量
     *
     * @return 错误信息
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("errorCode[").append(getErrorCode()).append("]\n");
        if (variables != null && variables.length != 0) {
            sb.append("variables[");
            for (int i = 0; i < variables.length; i++) {
                sb.append(variables[i]);
            }
            sb.append("]\n");
        }
        sb.append(super.toString());
        return sb.toString();
    }
}
