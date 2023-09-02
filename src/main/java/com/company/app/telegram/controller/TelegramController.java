package com.company.app.telegram.controller;

import com.company.app.telegram.TelegramFacade;
import com.company.app.telegram.domain.dto.TargetMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telegram")
public class TelegramController {

    @Autowired
    private TelegramFacade telegramFacade;

    /**
     * пример запроса: http://localhost:8080/telegram/say?message=hello
     */
    @GetMapping(value = "/say", produces = "application/json")
    public ResponseEntity<Boolean> say(@RequestParam String message) {
        telegramFacade.writeToEveryone(message);
        return ResponseEntity.ok(true);
    }

    @PostMapping(value = "/say", produces = "application/json")
    public ResponseEntity<Boolean> say(@RequestBody TargetMessage targetMessage) {
        telegramFacade.writeToTargetChat(targetMessage.getChatName(), targetMessage.getMessage());
        return ResponseEntity.ok(true);
    }
}
