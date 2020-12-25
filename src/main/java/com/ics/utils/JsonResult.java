package com.ics.utils;

/**
 * <p>Title: JsonResult</p>
 * <p>Description: 封装Json返回信息</p>
 * @author yi
 * @date 2017年10月25日 下午12:50:44
 */
public class JsonResult {
	
    private boolean success = true;
    private String msg = "操作成功";
    private int code;
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setFaild(){
		this.success = false;
	    this.msg = "操作失败";
	}
	public void setFaildMsg(String msg){
		this.success = false;
		this.msg = msg;
	}
}
