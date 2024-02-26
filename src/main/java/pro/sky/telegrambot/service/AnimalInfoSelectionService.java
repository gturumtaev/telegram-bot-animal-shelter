package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface AnimalInfoSelectionService {
    SendMessage datingRulesSelection(Long chat_id);

    SendMessage transportationSelection(Long chat_id);

    SendMessage documentsSelection(Long chat_id);

    SendMessage arrangementLimitedSelection(Long chat_id);

    SendMessage listReasonsSelection(Long chat_id);
}
