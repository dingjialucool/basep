package com.chinobot.common.utils;

import com.chinobot.common.domain.Result;
import com.chinobot.common.domain.ResultEnum;

public class ResultFactory {
    /**成功且带数据**/
    public static Result success(Object object){
        Result result = new Result();
        result.setSuccess(Boolean.TRUE);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**成功但不带数据**/
    public static Result success(){
        return success(null);
    }

    /**通用失败但不带数据**/
    public static Result error(){
        return error(null);
    }

    /**通用失败且带数据**/
    public static Result fail(Object object){
        Result result = new Result();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMsg(ResultEnum.FAIL.getMsg());
        result.setData(object);
        return result;
    }

    /**通用错误且带数据**/
    public static Result error(Object object){
        Result result = new Result();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMsg(ResultEnum.ERROR.getMsg());
        result.setData(object);
        return result;
    }

    /**指定code**/
    public static Result error(Integer code, String msg, Object object){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}
