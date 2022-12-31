package com.example.iot.server.core.protocol;

import java.util.Arrays;

/**
 * @author xupeng
 * @create 2022/11/10 15:41
 * @description
 * TCP MSG PROTOCOL V1
 * | Len  | Param           |
 * | ---- | --------------- |
 * | 2B   | magic           |
 * | 1B   | v               |
 * | 2B   | cmd             |
 * | 1B   | type            |
 * | 1B   | bizLine         |
 * | 4B   | msgId           |
 * | 4B   | length          | 消息长度
 * | ?B   | content         | 消息体: json数据
 */
public class TcpMsgProtocol implements Protocol {

    /**
     * Magic code
     */
    public static final byte[] MAGIC_CODE_BYTES = {(byte) 0xcc, (byte) 0xcc};

    public static final byte PROTOCOL_VERSION_1  = (byte) 1;

    public static final byte HEADER_LENGTH = (byte) 15;
    /**
     * 魔数 2字节
     */
    private byte[] magic;
    /**
     * 协议版本 1字节
     * 1: 第一版
     */
    private byte v = 0x1;
    /**
     * 命令 2字节
     */
    private short cmd;
    /**
     * 消息类型 1字节
     * 0: response
     * 1: request
     * 2: request_oneway
     */
    private byte type;
    /**
     * 业务线 1字节
     */
    private byte bizLine;
    /**
     * 消息ID 4字节
     */
    private int msgId;
    /**
     * content的长度 4字节
     */
    private int contentLen;
    /**
     * 消息体 ?字节
     */
    private byte[] content;

    public byte[] getMagic() {
        return magic;
    }

    public void setMagic(byte[] magic) {
        this.magic = magic;
    }

    public byte getV() {
        return v;
    }

    public void setV(byte v) {
        this.v = v;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getBizLine() {
        return bizLine;
    }

    public void setBizLine(byte bizLine) {
        this.bizLine = bizLine;
    }

    public int getContentLen() {
        return contentLen;
    }

    public void setContentLen(int contentLen) {
        this.contentLen = contentLen;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TcpMsgProtocol{" +
                ", cmd=" + cmd +
                ", type=" + type +
                ", bizLine=" + bizLine +
                ", msgId=" + msgId +
                ", contentLen=" + contentLen +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
