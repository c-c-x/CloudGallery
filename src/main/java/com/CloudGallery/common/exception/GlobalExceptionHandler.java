package com.CloudGallery.common.exception;

import com.CloudGallery.common.response.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 处理@RequestBody参数校验失败（JSON请求）
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody // 强制返回JSON
    public Result<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return buildValidationResult(ex.getBindingResult(), "请求体参数校验失败");
    }

    // 2. 处理表单参数校验失败（@ModelAttribute）
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<Map<String, String>> handleBindException(BindException ex) {
        return buildValidationResult(ex.getBindingResult(), "表单参数校验失败");
    }


    // 4. 处理请求体解析失败（JSON格式错误）
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMsg = ex.getCause() != null ? ex.getCause().getMessage() : "请求体格式错误";
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "请求解析失败", errorMsg);
    }

    // 5. 处理参数类型不匹配（如路径变量类型错误）
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMsg = String.format("参数'%s'类型错误，期望%s，实际值：%s",
                ex.getName(), ex.getRequiredType().getSimpleName(), ex.getValue());
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "参数类型错误", errorMsg);
    }

    // 6. 处理缺失必填参数（@RequestParam未传值）
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String errorMsg = String.format("缺失必填参数：%s（类型：%s）",
                ex.getParameterName(), ex.getParameterType());
        return Result.fail(HttpStatus.BAD_REQUEST.value(), "参数缺失", errorMsg);
    }

    // 通用错误结果构建方法
    private Result<Map<String, String>> buildValidationResult(BindingResult bindingResult, String errorTitle) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage() != null
                    ? error.getDefaultMessage()
                    : "字段校验失败");
        }
        return Result.fail(HttpStatus.BAD_REQUEST.value(), errorTitle, errorMap);
    }
}