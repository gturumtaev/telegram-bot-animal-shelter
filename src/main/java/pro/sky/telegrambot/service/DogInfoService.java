package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;

public interface DogInfoService {
    SendMessage arrangementPuppy(Long chat_id);

    SendMessage arrangementAdultDog(Long chat_id);

    SendMessage advicesDogHandler(Long chat_id);

    SendMessage recommendationsForProvenDogHandlers(Long chat_id);
}
