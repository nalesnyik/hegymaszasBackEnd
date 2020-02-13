package progmatic.hegymaszas.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/chat");  //ezen a néven hívható meg a @messagemapping(paramétéreként)
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws"); //TODO 1
        registry.addEndpoint("/ws").withSockJS();
    }

}
