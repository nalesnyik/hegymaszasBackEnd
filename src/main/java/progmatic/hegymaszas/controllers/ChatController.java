package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import progmatic.hegymaszas.modell.ChatMessage;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/chat")
    public String startChat(){
        return "chatpage";
    }

    @MessageMapping("/ws")
    //@SendToUser("queue/specific-user")
    public void sendSpecific(@Payload ChatMessage chatMessage, Principal user,
                             @Header("simpSessionId") String sessionId) throws Exception{
        ChatMessage out = new ChatMessage(
                chatMessage.getSender(),
                chatMessage.getContent(),
                new SimpleDateFormat("HH:mm").format(new Date()));
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getSentTo(),
                "/ws/queue/specific-user", out);

    }

    @MessageMapping("/ws.sendMessage")
    //@SendTo("/chat")
    public ChatMessage sendMessage(ChatMessage chatMessage) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new ChatMessage(chatMessage.getSender(), chatMessage.getContent(), time);
    }

    @MessageMapping("/ws.addUser")
    @SendTo("/queue/errors")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
