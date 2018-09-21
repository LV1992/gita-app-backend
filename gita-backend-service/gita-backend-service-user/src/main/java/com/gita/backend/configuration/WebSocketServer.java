package com.gita.backend.configuration;

import com.alibaba.fastjson.JSON;
import com.gita.backend.model.MessageModel;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket服务端  configurator = SpringConfigurator.class是为了使该类可以通过Spring注入。
 *
 * @author yihang.lv 2018/9/18、16:10
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{sessionKey}", configurator = MyEndpointConfigure.class)
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private static CopyOnWriteArraySet<Session> sessionStorage = new CopyOnWriteArraySet<>();

    @Autowired
    private Jedis jedis;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("sessionKey") String sessionKey, Session session) {
        this.session = session;
        addOnlineCount();           //在线数加1
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        //通过sessionKey取userId
        String uid = jedis.get(sessionKey);
        //绑定sessionId和userId
        jedis.set(getSessionIdRedisKey(session.getId()), uid);
        sessionStorage.add(session);
        try {
            sendMessage("连接成功", session);
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }
    //连接打开时执行
//    	@OnOpen
//    	public void onOpen(@PathParam("user") String user, Session session) {
//    		currentUser = user;
//    		System.out.println("Connected ... " + session.getId());
//    	}

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        sessionStorage.remove(this.session);  //从set中删除
        //解除sessionKey与sessionId绑定关系
        jedis.del(getSessionIdRedisKey(this.session.getId()));
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        MessageModel model = JSON.parseObject(message, MessageModel.class);
        //单独发送
        if (!model.isSendAll()) {
            //群发消息
            for (Session item : sessionStorage) {
                try {
                    String sessionId = item.getId();
                    //获取sessionId与userId绑定关系
                    String uid = jedis.get(getSessionIdRedisKey(sessionId));
                    //如果该用户还在线（与sessionId有绑定关系）则发送给指定用户
                    if (!Strings.isNullOrEmpty(uid)) {
                        //发送消息的sessionKey 和 已经保存的 sessionKey 相同且飞群发，则定向发送
                        if (model.getToUserId() != 0 && model.getToUserId() == Integer.parseInt(uid)) {
                            log.info("单发的消息:" + message);
                            sendMessage(message, item);
                            return;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                sendMessage2All(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    private static void sendMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    private static void sendMessage2All(String message) throws IOException {
        log.info("群发的消息:" + message);
        //群发消息
        for (Session item : sessionStorage) {
            item.getBasicRemote().sendText(message);
        }
    }


    /**
     * 其他服务可以使用
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        //TODO 其他服务调用发送消息
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    private String getSessionIdRedisKey(String sessionId) {
        return "SESSION_ID_REDIS_KEY_" + sessionId;
    }
}
