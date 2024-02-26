package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.DrivingDirectionsRepository;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.VolunteerService;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;

@Component
public class DogShelterServiceImpl implements ShelterService {
    private ShelterRepository shelterRepository;

    private DrivingDirectionsRepository drivingDirectionsRepository;
    private final VolunteerService volunteerService;

    @Autowired
    public DogShelterServiceImpl(ShelterRepository shelterRepository, DrivingDirectionsRepository drivingDirectionsRepository, VolunteerService volunteerService) {
        this.shelterRepository = shelterRepository;
        this.drivingDirectionsRepository = drivingDirectionsRepository;
        this.volunteerService = volunteerService;
    }

    public DogShelterServiceImpl(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Override
    public SendMessage boardMarkup(Long chat_id) {
        String smile_dog = EmojiParser.parseToUnicode(":dog:");
        String smile_arrow = EmojiParser.parseToUnicode(":arrow_left:");

        String smile_point_down = EmojiParser.parseToUnicode(":point_down:");
        KeyboardButton keyboardButton = new KeyboardButton(smile_dog + " Узнать информацию о приюте");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton);

        KeyboardButton keyboardButton2 = new KeyboardButton(smile_dog + " Как взять животное из приюта");
        keyboardMarkup.addRow(keyboardButton2);
        KeyboardButton keyboardButton3 = new KeyboardButton(smile_dog + " Прислать отчет о питомце");
        keyboardMarkup.addRow(keyboardButton3);
        KeyboardButton keyboardButton4 = new KeyboardButton(smile_dog + " Связаться c волонтером");
        keyboardMarkup.addRow(keyboardButton4);
        KeyboardButton keyboardButton5 = new KeyboardButton(smile_arrow + " В начало");
        keyboardMarkup.addRow(keyboardButton5);

        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        String text_bot = " Если ни один из вариантов не подходит, то бот может позвать волонтера. \n" + smile_point_down + " Выберите действие в меню" + smile_point_down;
        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    @Override
    public SendMessage shelterBoardMarkup(Long chat_id) {
        String smile_dog = EmojiParser.parseToUnicode(":dog:");
        String smile_arrow = EmojiParser.parseToUnicode(":arrow_left:");

        KeyboardButton keyboardButton = new KeyboardButton(smile_dog + "Рассказать о приюте");
        KeyboardButton keyboardButton2 = new KeyboardButton(smile_dog + "Расписание работы");
        KeyboardButton keyboardButton3 = new KeyboardButton(smile_dog + "Адрес приюта");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton, keyboardButton2);

        KeyboardButton keyboardButton4 = new KeyboardButton(smile_dog + "Схема проезда");
        KeyboardButton keyboardButton5 = new KeyboardButton(smile_dog + "Оформление пропуска");
        KeyboardButton keyboardButton6 = new KeyboardButton(smile_dog + "Оставить номер телефона");
        keyboardMarkup.addRow(keyboardButton5, keyboardButton4);

        keyboardMarkup.addRow(keyboardButton6, keyboardButton3);


        KeyboardButton keyboardButton7 = new KeyboardButton(smile_dog + "Техника безопасности");
        KeyboardButton keyboardButton8 = new KeyboardButton(smile_dog + " Связаться c волонтером");
        keyboardMarkup.addRow(keyboardButton7, keyboardButton8);
        KeyboardButton keyboardButton9 = new KeyboardButton(smile_arrow + " В начало");
        keyboardMarkup.addRow(keyboardButton9);


        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        String text_bot = "Выберите какую информацию вы хотели бы узнать. Если ни один из вариантов не подходит, то бот может позвать волонтера.";
        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    @Override
    public SendMessage getWorkScheduleFromDB(Long chat_id) {
        String workSchedule = shelterRepository.findById(2L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getWorkSchedule();
        return new SendMessage(chat_id, workSchedule);
    }

    @Override
    public SendMessage getAddressFromDB(Long chat_id) {
        String address = shelterRepository.findById(2L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getAddress();
        return new SendMessage(chat_id, address);
    }

    @Override
    public SendPhoto getDrivingDirections(Long chat_id) {
        return new SendPhoto(chat_id, Objects.requireNonNull(drivingDirectionsRepository.findById(2L).orElse(null)).getFilePath()).caption("Вот схема проезда к нашему приюту для собак.");
    }

    @Override
    public SendMessage getShelterPhoneNumberSecurityFromDB(Long chat_id) {
        String securityPhone = shelterRepository.findById(2L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getSecurity();
        return new SendMessage(chat_id, "Для оформления пропуска на территорию приюта позвоните по номеру телефона - " + securityPhone);
    }

    @Override
    public SendMessage getShelterSafetyPrecautionsSecurityFromDB(Long chat_id) {
        String safetyPrecautions = shelterRepository.findById(2L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getSafetyPrecautions();
        return new SendMessage(chat_id, safetyPrecautions);
    }
    @Override
    public SendMessage shelterStory(Long chat_id) {
        return new SendMessage(chat_id, "Наш приют занимается поиском новых хозяев для бездомных собак");
    }

    @Override
    public SendMessage getVolunteersShelter(Long chat_id) {
        List<Volunteer> volunteersDog = volunteerService.findVolunteerByShelterId(2L);
        SendMessage sendMessage;
        StringBuilder volunteersNumberResult = new StringBuilder();
        for (Volunteer volunteer:volunteersDog) {
            volunteersNumberResult.append("Телефонный номер волонтера: ").append(volunteer.getFirstName()).append(": ").append(volunteer.getPhoneNumber()).append("\n");
        }
        if (volunteersNumberResult.length() > 0) {
            sendMessage = new SendMessage(chat_id, "Вот контакты наших волонтеров:\n" + volunteersNumberResult);
        } else {
            sendMessage = new SendMessage(chat_id, "Нет доступных волонтеров в приюте.");
        }

        return sendMessage;
    }
}
