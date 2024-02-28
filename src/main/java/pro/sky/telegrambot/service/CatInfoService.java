package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface CatInfoService {

    SendMessage arrangementKitty(Long chat_id);

    SendMessage arrangementAdultCat(Long chat_id);
}
