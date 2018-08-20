package com.test.entity;

public class Result {

    private Integer code;
    private String msg;
    private Object data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 设置返回
     * @param code
     * @param message
     * @param obj
     * @return
     */
    public static Result setResult(Integer code,String message,Object obj) {

        Result result = new Result();
        result.setCode(code);
        result.setMsg(message);
        result.setData(obj);
        return result;
    }
}
