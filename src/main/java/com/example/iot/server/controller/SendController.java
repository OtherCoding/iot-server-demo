package com.example.iot.server.controller;

import com.example.iot.server.common.util.SendUtil;
import com.example.iot.server.core.session.Connection;
import com.example.iot.server.core.session.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xupeng
 * @create 2022/12/1 13:33
 * @description
 */
@Slf4j
@RestController
public class SendController {

    @Resource
    private ConnectionManager connectionManager;

    @RequestMapping("/send")
    public void sendMessage(String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            log.warn("clientId param cannot null");
            return;
        }

        Connection connection = connectionManager.getConnection(clientId);
        if (connection == null) {
            log.warn("connection is not exist");
            return;
        }

        String msg = "hello world";
        SendUtil.sendMsg(connection.getChannel(), (short) 1, (byte) 1, (byte) 66, msg);
    }
}
