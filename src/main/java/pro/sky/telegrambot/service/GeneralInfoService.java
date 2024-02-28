package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface GeneralInfoService {
    SendMessage dateRules(Long chat_id);

    SendMessage transportationRecommendation(Long chat_id);

    SendMessage documentsList(Long chat_id);

    SendMessage arrangementLimitedPet(Long chat_id);

    SendMessage listReasons(Long chat_id);
}
