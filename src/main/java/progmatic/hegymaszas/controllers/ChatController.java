package progmatic.hegymaszas.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import progmatic.hegymaszas.modell.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ChatController {

    @MessageMapping("/ws/queue")
    @SendTo("/queue/reply")
    public ChatMessage sendMessage(ChatMessage chatMessage) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return chatMessage;
    }

    @MessageMapping("/ws.addUser")
    @SendTo("/queue/errors")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
