# SpringBoot 整合webSocket实现一对一消息推送和广播消息推送
#### SpringBoot 基础环境
参考官方文档
#### maven核心依赖
```xml
<!--SpringBoot WebSocket -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```

#### 前期准备
##### 常量类
```java
//webSocket相关配置
//链接地址
public static String WEBSOCKETPATHPERFIX = "/ws-push";
public static String WEBSOCKETPATH = "/endpointWisely";
//消息代理路径
public static String WEBSOCKETBROADCASTPATH = "/topic";
//前端发送给服务端请求地址
public static final String FORETOSERVERPATH = "/welcome";
//服务端生产地址,客户端订阅此地址以接收服务端生产的消息
public static final String PRODUCERPATH = "/topic/getResponse";
//点对点消息推送地址前缀
public static final String P2PPUSHBASEPATH = "/user";
//点对点消息推送地址后缀,最后的地址为/user/用户识别码/msg
public static final String P2PPUSHPATH = "/msg";
```
#### 接受前端消息实体
```java
public class WiselyMessage {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
#### 后台响应实体
```java
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
```
#### 配置websocket
```java
@Configuration
// @EnableWebSocketMessageBroker 注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（Controller）
// 开始支持@MessageMapping,就像使用@RequestMapper一样。
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 注意extends AbstractWebSocketMessageBrokerConfigurer已过时

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
```
#### service以及impl
```java
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
----------
public interface WebSocketService {
    void sendMsg(WiselyResponse msg);

    void sendUsersMsg(List<String> users, WiselyResponse msg);
}
```
#### controller
```java
@Controller
public class WebSocketController {
    @Resource
    private WebSocketService webSocketService;

    @MessageMapping(Constant.FORE_TO_SERVER_PATH)//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @SendTo(Constant.PRODUCER_PATH)//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public WiselyResponse say(WiselyMessage message) {
        List<String> users = new ArrayList<>();
        users.add("user1"); //此处写死只是为了方便测试,此值需要对应页面中订阅个人消息的userId
        webSocketService.sendUsersMsg(users,new WiselyResponse("admin hello!!!"));
        return new WiselyResponse("Welcome, " + message.getName()+"!");
    }
}
```