package com.uwbhome.pm.websocket;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {
    private static SystemWebSocketHandler single=null;
    //静态工厂方法
    public static SystemWebSocketHandler getInstance() {
         if (single == null) {
             single = new SystemWebSocketHandler();
         }
        return single;
    }

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	System.out.println("ConnectionEstablished");
        //有客户端连接进来，将这个客户端加入到用户列表中
    	users.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	System.out.println("收到来自客户端数据:"+message.getPayload());
    	TextMessage msg = new TextMessage("服务器端消息!");
    	msg.asBytes();
        sendMessageToUsers(msg);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    	System.out.println("handleTransportError");
        if(session.isOpen()){
            session.close();
        }
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	System.out.println("afterConnectionClosed");
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}