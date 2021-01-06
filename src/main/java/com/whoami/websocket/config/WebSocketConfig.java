package com.whoami.websocket.config;

import com.whoami.websocket.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Title:
 * @ClassName: com.whoami.websocket.config.WebSocketConfig.java
 * @Description: 配置websocket
 *
 * @Copyright 2020 yunchao - Powered By yingyun
 * @author: whoamizq
 * @date:  2021-01-06 16:58
 * @version V1.0
 */
@Configuration
// @EnableWebSocketMessageBroker 注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（Controller）
// 开始支持@MessageMapping,就像使用@RequestMapper一样。
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 注意extends AbstractWebSocketMessageBrokerConfigurer已过时
    /**
     * @Description //TODO 创建ServerEndpointExporter 组件，交由SpringIoc容器管理，他会自动扫描注册应用中所有的@ServerEndpoint
     * @Date 15:02 2020/7/18
     * @Param []
     * @return org.springframework.web.socket.server.standard.ServerEndpointExporter
     *
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }*/

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个Stomp节点（endpoint）,并指定使用sockJS协议
        registry.addEndpoint(Constant.WEBSOCKET_PATH).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 服务端发送消息给客户端的域，多个逗号隔开
        registry.enableSimpleBroker(Constant.WEBSOCKET_BROADCAST_PATH, Constant.P2P_PUSH_BASE_PATH);
        // 定义一对一推送的前缀
        registry.setUserDestinationPrefix(Constant.P2P_PUSH_BASE_PATH);
        // 定义websocket后缀
        registry.setApplicationDestinationPrefixes(Constant.WEBSOCKET_PATH_PREFIX);
    }
}
