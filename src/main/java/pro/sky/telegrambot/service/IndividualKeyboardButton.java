package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface IndividualKeyboardButton {
    SendMessage selectionInfo(Long chat_id);
}