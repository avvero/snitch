package com.github.avvero.snitch.controller;

import com.github.avvero.snitch.dto.bf.ConversationAccount;
import com.github.avvero.snitch.dto.bf.ConversationMessage;
import com.github.avvero.snitch.dto.bf.TextMessage;
import com.github.avvero.snitch.service.BotFrameworkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fxdev-belyaev-ay
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private BotFrameworkService botFrameworkService;

    @RequestMapping(value = "/endpoint", method = RequestMethod.POST)
    public void endpoint(@RequestBody ConversationMessage message) {
        log.info("ENDPOINT START");
        log.info(message.toString());

        ConversationMessage echo = new ConversationMessage();
        echo.setChannelId(message.getChannelId());
        echo.setConversation(message.getConversation());
        echo.setFrom(message.getRecipient());
        echo.setRecipient(message.getFrom());
        echo.setServiceUrl(message.getServiceUrl());
        echo.setType("message");
        echo.setText("<ss type=\"hi\">(wave)</ss>");
        botFrameworkService.send(echo);
        log.info("ENDPOINT END");
    }

    @RequestMapping(value = "/say/{conversationId}", method = RequestMethod.POST)
    public void say(@RequestParam String conversationId, @RequestBody TextMessage message) {
        log.info("ENDPOINT START");
        log.info(message.toString());

        ConversationMessage echo = new ConversationMessage();
        echo.setConversation(new ConversationAccount(null, conversationId, null));
        echo.setServiceUrl("https://skype.botframework.com");
        echo.setType("message");
        echo.setText(message.getText());
        botFrameworkService.send(echo);
        log.info("ENDPOINT END");
    }
}
