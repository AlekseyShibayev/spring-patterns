package com.company.app.telegram.component;

import com.company.app.telegram.component.config.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramDistributionHub {

    private final IncomingMessageHandler incomingMessageHandler;
    private final OutgoingMessageHandler outgoingMessageHandler;
    private final TelegramBotConfig telegramBotConfig;

    public void read(Update update) {
        incomingMessageHandler.process(update);
    }

    public void writeToEveryone(Object message) {
        outgoingMessageHandler.sendToEveryone(message);
    }

    public void writeToTargetChat(String chatName, Object message) {
        outgoingMessageHandler.sendToTargetChat(chatName, message);
    }

    public void writeToTargetChat(SendMessage sendMessage) {
        telegramBotConfig.write(sendMessage);
    }

}