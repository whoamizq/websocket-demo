package com.whoami.websocket.tomcatWebSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * @Author zhangzhenQuan
 * @Description //TODO
 * @Date 14:28 2020/7/18
 **/
//标识此类为Tomcat的websocket服务终端，/websocket/yy.action 是客户端连接请求的路径
//@ServerEndpoint(value = "/websocket/yy.action")
//@Component //将本类交由springIoc容器管理
public class ServerEndpoints {
    private static Logger logger = LoggerFactory.getLogger(ServerEndpoints.class);

    //用Set来存储客户端连接
    private static Set<Session> sessionSet = new HashSet<>();

    /**
     * @Description //TODO 连接成功后自动触发
     * String queryString = session.getQueryString(); //获取请求地址中的查询字符串
     * Map<String,List<String>> parameterMap = session.getPathParameters();
     * URI uri = session.getRequestURI();
     * @Date 14:36 2020/7/18
     * @Param [session] 表示一个连接会话，整个连接会话过程中它都是固定的，每个不同的连接session不同
     * @return void
     **/
    @OnOpen
    public void afterConnectionEstablished(Session session){
        sessionSet.add(session);
        logger.info("新客户端加入，session id=" + session.getId()
                + ",当前客户端个数为：" + sessionSet.size());

        session.getAsyncRemote().sendText("我是服务器，恭喜您，连接成功！");
    }

    /**
     * @Description //TODO 连接断开后自动触发，连接断开后，应该清除掉 session集合中的值
     * @Date 14:47 2020/7/18
     * @Param [session]
     * @return void
     **/
    @OnClose
    public void afterConnectionClosed(Session session){
        sessionSet.remove(session);
        logger.info("客户端断开，session id=" + session.getId()
                + "，当前客户端个数为：" + sessionSet.size());
    }

    /**
     * @Description //TODO 收到客户端消息后自动触发
     * @Date 14:54 2020/7/18
     * @Param [session, textMessage 客户端传来的文本消息]
     * @return void
     **/
    @OnMessage
    public void handleMessage(Session session,String textMessage){
        try {
            logger.info("接收到客户端信息，session id=" + session.getId() + ":" + textMessage);
            /**
             * @Description //TODO 原样回复文本消息
             * getBasicRemote():同步发送
             * getAsyncRemote():异步发送
             **/
            session.getBasicRemote().sendText(textMessage);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * @Description //TODO 信息传输错误后
     * @Date 14:59 2020/7/18
     * @Param [session, throwable]
     * @return void
     **/
    @OnError
    public void handleTransportError(Session session,Throwable throwable){
        System.out.println("shake client And server handleTransportError,session.getId()="
                + session.getId() + "--" + throwable.getMessage());
        logger.error("与客户端 session id=" + session.getId() + "通信错误...");
    }
}
