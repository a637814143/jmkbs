package com.dali.movie.model;

import com.dali.movie.enums.ResultStatus;
import lombok.Data;

@Data
public class Result<T> {
    // 业务码
    private ResultStatus code;
    // 错误信息
    private String errMsg;
    // 接口响应的数据
    private T data;


    //检查出就是 Lombok 的问题
//    // 手动添加setCode方法
//    public void setCode(ResultStatus code) {
//        this.code = code;
//    }
//
//    // 同时手动添加其他需要的setter（setErrMsg、setData）
//    public void setErrMsg(String errMsg) {
//        this.errMsg = errMsg;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

    public static <T> Result<T> success(T data){
        Result result = new Result();
        result.setCode(ResultStatus.SUCCESS);
        result.setErrMsg("");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String errMsg){
        Result result = new Result();
        result.setCode(ResultStatus.FAIL);
        result.setErrMsg(errMsg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> fail(String errMsg, T data){
        Result result = new Result();
        result.setCode(ResultStatus.FAIL);
        result.setErrMsg(errMsg);
        result.setData(data);
        return result;
    }
}
