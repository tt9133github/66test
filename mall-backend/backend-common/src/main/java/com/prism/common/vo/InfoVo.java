package com.prism.common.vo;

public class InfoVo
{
    private String msg;
    /**
     * 返回请求状态
     * 0失败 1成功
     * @param status
     */
    private HttpStatus status;
    private Object data;

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public HttpStatus getStatus()
    {
        return status;
    }

    public void setStatus(HttpStatus status)
    {
        this.status = status;
    }

    public InfoVo()
    {
    }

    public InfoVo(String msg)
    {
        this.msg = msg;
    }

    public InfoVo(String msg, Object data)
    {
        this.msg = msg;
        this.data = data;
    }

    public InfoVo(String msg, HttpStatus status, Object data)
    {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public InfoVo(String msg, HttpStatus status)
    {
        this.msg = msg;
        this.status = status;
    }

    public enum HttpStatus {
        Ok, Failed, Timeout;
    }
}
