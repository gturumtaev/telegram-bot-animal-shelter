package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface CatInfoSelectionService {
    SendMessage selectionInfo(Long chat_id);

    SendMessage kittenArrangementSelection(Long chat_id);

    SendMessage arrangementAdultSelectionCat(Long chat_id);
}
