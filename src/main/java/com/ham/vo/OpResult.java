package com.ham.vo;

import java.io.Serializable;

/**
 * Created by hamsbon on 2017/2/15.
 */
public class OpResult<T> implements Serializable{

    private boolean success;

    private T data;

    private String opMsg;

    @Override
    public String toString() {
        return "OpResult{" +
                "success=" + success +
                ", data=" + data +
                ", opMsg='" + opMsg + '\'' +
                '}';
    }

    public OpResult() {
    }

    public OpResult(boolean success) {
        this.success = success;
    }

    public OpResult(boolean success, String opMsg) {
        this.success = success;
        this.opMsg = opMsg;
    }

    public OpResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public OpResult(boolean success, T data, String opMsg) {
        this.success = success;
        this.data = data;
        this.opMsg = opMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getOpMsg() {
        return opMsg;
    }

    public void setOpMsg(String opMsg) {
        this.opMsg = opMsg;
    }
}
