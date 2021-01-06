package com.whoami.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author zhangzhenQuan
 * @Description //TODO
 * @Date 15:00 2020/7/18
 **/
@Configuration
public class WebSocketConfig {
    
    /**
     * @Description //TODO 创建ServerEndpointExporter 组件，交由SpringIoc容器管理，他会自动扫描注册应用中所有的@ServerEndpoint
     * @Date 15:02 2020/7/18
     * @Param []
     * @return org.springframework.web.socket.server.standard.ServerEndpointExporter
     **/
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
