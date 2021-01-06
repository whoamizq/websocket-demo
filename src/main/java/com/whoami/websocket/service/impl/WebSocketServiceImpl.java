package com.whoami.websocket.service.impl;

import com.whoami.websocket.common.Constant;
import com.whoami.websocket.dto.res.WiselyResponse;
import com.whoami.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @description: 广播发给所有在线用户
     * @param:  msg
     * @return: void
     * @exception: 
     * 
     * @author: whoamizq
     * @date:  2021-01-06 17:19
     */
    @Override
    public void sendMsg(WiselyResponse msg){
        template.convertAndSend(Constant.PRODUCER_PATH, msg);
    }

    /**
     * @description: 发给指定用户
     * @param:  用户列表，消息
     * @return:
     * @exception: 
     * 
     * @author: whoamizq
     * @date:  2021-01-06 17:22
     */
    @Override
    public void sendUsersMsg(List<String> users, WiselyResponse msg){
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, Constant.P2P_PUSH_PATH, msg);
        });
    }
}
