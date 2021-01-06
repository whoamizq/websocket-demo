package com.whoami.websocket.dto.res;

/**
 * @Title:
 * @ClassName: com.whoami.websocket.dto.res.WiselyResponse.java
 * @Description: 后台发送消息实体
 *
 * @Copyright 2020 yunchao - Powered By yingyun
 * @author: whoamizq
 * @date:  2021-01-06 16:55
 * @version V1.0
 */
public class WiselyResponse {
    private String responseMessage;

    public WiselyResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
