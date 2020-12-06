package com.dragon.score.handle;


import com.dragon.score.model.ResultBean;
import com.dragon.score.model.exception.CommonException;
import com.dragon.score.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@Slf4j
@RestControllerAdvice
public class ExceptionHandle {
    /**
     * 自定义业务异常
     */
    @ExceptionHandler(value = CommonException.class)
    public ResultBean commonExHandle(CommonException e) {
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数缺失异常（请求体）
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean exHandle(MethodArgumentNotValidException e) {
        return ResultUtils.error(400, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 请求体缺失
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultBean exHandle(HttpMessageNotReadableException e) {
        return ResultUtils.error(400, "请求体缺失");
    }

    /**
     * 参数缺失异常
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResultBean exHandle(MissingServletRequestParameterException e) {
        return ResultUtils.error(400, e.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultBean exHandle(HttpRequestMethodNotSupportedException e) {
        return ResultUtils.error(405, e.getMessage());
    }

    /**
     * 传输文件过大异常
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResultBean exHandle(MaxUploadSizeExceededException e) {
        return ResultUtils.error(406, e.getMessage());
    }

    /**
     * SQL异常
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public ResultBean exHandle(BadSqlGrammarException e) {
        log.error("SQL异常：", e.getMessage());
        return ResultUtils.error(501, e.getCause().getMessage());
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(value = Exception.class)
    public ResultBean exHandle(Exception e) {
        log.error("未知异常，原因是：", e.getMessage());
        return ResultUtils.error(500, e.getMessage());
    }
}
