package com.company.app.telegram.incoming_message_handler.button.service;

import com.company.app.telegram.config.TelegramBotConfig;
import com.company.app.telegram.domain.entity.Chat;
import com.company.app.telegram.domain.enums.Role;
import com.company.app.telegram.domain.repository.ChatRepository;
import com.company.app.telegram.domain.spec.ChatSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ButtonFactory {

    private static final InlineKeyboardButton TG_OFF_BUTTON = new InlineKeyboardButton("Отключить уведомления");
    private static final InlineKeyboardButton TG_ON_BUTTON = new InlineKeyboardButton("Включить уведомления");
    private static final InlineKeyboardButton WB_MAIN_MENU = new InlineKeyboardButton("Управление wildberries");
    private static final InlineKeyboardButton WB_DL_SHOW = new InlineKeyboardButton("Покажи что нашёл");
    private static final InlineKeyboardButton WB_DL_SHOW_MY_DESIRES = new InlineKeyboardButton("Покажи мои желания");
    private static final InlineKeyboardButton WB_DL_ADD = new InlineKeyboardButton("Добавить желания");
    private static final InlineKeyboardButton WB_DL_REMOVE = new InlineKeyboardButton("Удалить желания");
    private static final InlineKeyboardButton ER_BUTTON = new InlineKeyboardButton("Последний курс aliexpress");
    private static final InlineKeyboardButton ADMIN_MAIN_MENU = new InlineKeyboardButton("Админка");
    private static final InlineKeyboardButton ADMIN_WB_DL_SELECT = new InlineKeyboardButton("wb desire lot select *");
    private static final InlineKeyboardButton ADMIN_WB_DL_MANUAL_REFRESH = new InlineKeyboardButton("wb desire lot manual refresh");

    private final ChatRepository chatRepository;

    public InlineKeyboardMarkup mainMenuButtons(Update update) {
        String role = getUserRole(update.getMessage());

        TG_OFF_BUTTON.setCallbackData("TG_OFF");
        TG_ON_BUTTON.setCallbackData("TG_ON");
        WB_MAIN_MENU.setCallbackData("WB_MAIN_MENU");
        ER_BUTTON.setCallbackData("EX");

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        rowsInLine.add(List.of(TG_OFF_BUTTON));
        rowsInLine.add(List.of(TG_ON_BUTTON));
        rowsInLine.add(List.of(WB_MAIN_MENU));
        rowsInLine.add(List.of(ER_BUTTON));

        if (Role.OWNER.name().equals(role)) {
            ADMIN_MAIN_MENU.setCallbackData("ADMIN_MAIN_MENU");
            rowsInLine.add(List.of(ADMIN_MAIN_MENU));
        }

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

    private String getUserRole(Message message) {
        String chatName = String.valueOf(message.getChatId());
        Chat chat = chatRepository.findOne(ChatSpecification.chatNameIs(chatName))
                .orElseThrow(() -> new IllegalArgumentException("Chat with name [%s] is absent".formatted(chatName)));
        return chat.getUserInfo().getRole();
    }

    public InlineKeyboardMarkup wildberriesMenuButtons() {
        WB_DL_SHOW.setCallbackData("WB_DL_SHOW");
        WB_DL_SHOW_MY_DESIRES.setCallbackData("WB_DL_SHOW_MY_DESIRES");
        WB_DL_ADD.setCallbackData("WB_DL_ADD");
        WB_DL_REMOVE.setCallbackData("WB_DL_REMOVE");

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        rowsInLine.add(List.of(WB_DL_SHOW));
        rowsInLine.add(List.of(WB_DL_SHOW_MY_DESIRES));
        rowsInLine.add(List.of(WB_DL_ADD));
        rowsInLine.add(List.of(WB_DL_REMOVE));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

    public ReplyKeyboard adminMenuButtons() {
        ADMIN_WB_DL_SELECT.setCallbackData("ADMIN_WB_DL_SELECT");
        ADMIN_WB_DL_MANUAL_REFRESH.setCallbackData("ADMIN_WB_DL_MANUAL_REFRESH");

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        rowsInLine.add(List.of(ADMIN_WB_DL_SELECT));
        rowsInLine.add(List.of(ADMIN_WB_DL_MANUAL_REFRESH));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

}