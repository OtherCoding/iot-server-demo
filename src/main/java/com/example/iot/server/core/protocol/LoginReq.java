package com.example.iot.server.core.protocol;

/**
 * @author xupeng
 * @create 2022/12/14 14:05
 * @description 登录请求
 */
public class LoginReq {

    private String clientId;

    public LoginReq(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "clientId='" + clientId + '\'' +
                '}';
    }
}
