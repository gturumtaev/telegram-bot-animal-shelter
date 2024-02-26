package pro.sky.telegrambot.service;


import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

public interface ShelterService {
    SendMessage boardMarkup(Long chat_id);

    SendMessage shelterBoardMarkup(Long chat_id);

    SendMessage getWorkScheduleFromDB(Long chat_id);

    SendMessage getAddressFromDB(Long chat_id);

    SendPhoto getDrivingDirections(Long chat_id);

    SendMessage getShelterPhoneNumberSecurityFromDB(Long chat_id);

    SendMessage getShelterSafetyPrecautionsSecurityFromDB(Long chat_id);

    SendMessage shelterStory(Long chat_id);

    SendMessage getVolunteersShelter(Long chat_id);
}