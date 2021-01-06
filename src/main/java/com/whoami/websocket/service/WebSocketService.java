package com.whoami.websocket.service;

import com.whoami.websocket.dto.res.WiselyResponse;

import java.util.List;

public interface WebSocketService {
    void sendMsg(WiselyResponse msg);

    void sendUsersMsg(List<String> users, WiselyResponse msg);
}
