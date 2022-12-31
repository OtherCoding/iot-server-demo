package com.example.iot.server.core.command;

/**
 * @author xupeng
 * @create 2022/11/29 10:50
 * @description 远程命令代码代表一种特定的远程命令，每种命令有自己的编号
 * 下行：服务端->设备，使用正数
 * 上行：设备->服务端，使用负数
 */
public interface CommandCode {

    /**
     * 0 被心跳占用，不能被其他命令所使用
     */
    short HEARTBEAT_VALUE = 0;
    /**
     * -1 客户端发起连接请求
     */
    short CONNECT_REQ_VALUE = -1;
    /**
     * 1 服务端响应连接请求
     */
    short CONNECT_RESP_VALUE = 1;
    /**
     * -2 客户端发起断连请求
     */
    short DISCONNECT_REQ_VALUE = -2;

    /**
     * 例子：下行:开空调指令
     */
    short OPEN_AIR_CONDITIONER_VALUE = 1000;
    /**
     * 例子: 上行:上报空调温度
     */
    short REPORT_AIR_CONDITIONER_TEMPERATURE_VALUE = -1000;

}
