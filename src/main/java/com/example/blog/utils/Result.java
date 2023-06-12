package com.example.blog.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private String code;
    private String msg;
    private Object data;
    public static Result success(Object data){
        Result result=new Result();
        result.setCode("200");
        result.setData(data);
        result.setMsg("操作成功");
        return result;
    }
    public static Result success(String message,Object data){
        Result result=new Result();
        result.setCode("200");
        result.setData(data);
        result.setMsg(message);
        return result;
    }
    public static Result fail(String message){
        Result result=new Result();
        result.setCode("400");
        result.setMsg(message);
        result.setData(null);
        return result;
    }
    public static Result fail(String message,Object data){
        Result result=new Result();
        result.setCode("400");
        result.setData(data);
        result.setMsg(message);
        return result;
    }

}
