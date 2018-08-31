package com.gita.backend.configuartion;

import com.gita.backend.dto.common.Response;
import com.gita.backend.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

/**全局异常捕获
 * @author yihang.lv 2018/8/22、17:06
 */
@ControllerAdvice
@ResponseBody
public class GlobalException {


    /**
     * 统一异常处理
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({ Exception.class })
    public Response handleArrayIndexOutOfBoundsException(Exception e) {
        e.printStackTrace();
        return Response.error();
    }

    /**
     * 处理所有业务异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    Response handleBusinessException(BusinessException e){
        return Response.error(Integer.valueOf(e.getErrorCode()),e.getErrorMessage());
    }

    /**
     * 捕获MethodArgumentNotValidException
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object bindException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();

        String errorMesssage = "校验失败:";

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + ", ";
        }
        HashMap<String,Object> objectHashMap = new HashMap<>();
        objectHashMap.put("success",false);
        objectHashMap.put("errorCode",400);
        objectHashMap.put("errorMsg",errorMesssage);
        return objectHashMap;
    }
}
