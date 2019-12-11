package com.demo.sabhttp;

/**
 * Author：xuejingfei
 * <p>
 * Description：
 * <p>
 * Date：2019-07-27 16:38
 */
public class ResponseResult {
    private String resultCode;
    private String reason;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "resultCode='" + resultCode + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
