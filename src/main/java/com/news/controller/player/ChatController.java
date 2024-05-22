package com.news.controller.player;

import com.news.pojo.req.chat.ChatMessage;
import com.news.pojo.req.chat.MessageType;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "聊天室-消息")
public class ChatController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @SubscribeMapping("/topic/public")
    public ChatMessage subscribePublic(){
        ChatMessage msg = new ChatMessage();
        msg.setContent("订阅公共消息成功通知。");
        msg.setType(MessageType.JOIN);
        msg.setSender("system");
        return msg;
    }

    @SubscribeMapping("/room/{roomId}}")
    public ChatMessage subscribeRoom(@DestinationVariable String roomId) {
        ChatMessage msg = new ChatMessage();
        msg.setContent("订阅聊天室["+roomId+"]消息成功通知。");
        msg.setType(MessageType.JOIN);
        msg.setSender("system");
        return msg;
    }

    @SubscribeMapping("/user/${token}/chat")
    public ChatMessage userChat(@DestinationVariable String token) {
        ChatMessage msg = new ChatMessage();
        msg.setContent("私聊通道["+token+"]订阅成功通知。");
        msg.setType(MessageType.JOIN);
        msg.setSender("system");
        return msg;
    }

    @MessageMapping("/user/sendmsg")
    @SendTo("/topic/public")
    public ChatMessage userSendmsg(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @RequestMapping(path = "/sendmsg", method = RequestMethod.POST)
    public void sendMessage(@RequestBody ChatMessage chatMessage){
        simpMessagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
}
