package com.whoami.websocket.controller;

import com.google.common.collect.Lists;
import com.whoami.websocket.common.Constant;
import com.whoami.websocket.dto.req.WiselyMessage;
import com.whoami.websocket.dto.res.WiselyResponse;
import com.whoami.websocket.service.WebSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class WebSocketController {
    @Resource
    private WebSocketService webSocketService;

    @MessageMapping(Constant.FORE_TO_SERVER_PATH)//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @SendTo(Constant.PRODUCER_PATH)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public WiselyResponse say(WiselyMessage message) {
        List<String> users = Lists.newArrayList();
        users.add("d892bf12bf7d11e793b69c5c8e6f60fb"); //此处写死只是为了方便测试,此值需要对应页面中订阅个人消息的userId
        webSocketService.sendUsersMsg(users,new WiselyResponse("admin hello!!!"));
        return new WiselyResponse("Welcome, " + message.getName()+"!");
    }
}
