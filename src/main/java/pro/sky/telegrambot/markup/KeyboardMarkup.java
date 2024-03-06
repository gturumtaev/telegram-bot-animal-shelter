package pro.sky.telegrambot.markup;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import static pro.sky.telegrambot.constans.Constans.*;

@Component
public class KeyboardMarkup {

    public static SendMessage boardMarkupCatAndDog(Long chat_id) {
        KeyboardButton keyboardButtonCat = new KeyboardButton(BUTTON_CAT_SHELTER);
        KeyboardButton keyboardButtonDog = new KeyboardButton(BUTTON_DOG_SHELTER);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButtonCat);

        keyboardMarkup.addRow(keyboardButtonDog);

        keyboardMarkup.resizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(chat_id, WELCOME_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    public SendMessage boardMarkupStageZeroCat(Long chat_id) {
        KeyboardButton keyboardButton = new KeyboardButton(BUTTON_INFO_CAT_SHELTER);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton);

        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_STAGE_2_CAT);
        keyboardMarkup.addRow(keyboardButton2);
        KeyboardButton keyboardButton3 = new KeyboardButton(BUTTON_REPORT_CAT);
        keyboardMarkup.addRow(keyboardButton3);
        KeyboardButton keyboardButton4 = new KeyboardButton(BUTTON_VOLUNTEER_CAT_SHELTER);
        keyboardMarkup.addRow(keyboardButton4);
        KeyboardButton keyboardButton5 = new KeyboardButton(BUTTON_TO_THE_BEGINNING);
        keyboardMarkup.addRow(keyboardButton5);

        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        SendMessage sendMessage = new SendMessage(chat_id, INFO_STAGE_0_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    public SendMessage boardMarkupStageOneCat(Long chat_id) {
        KeyboardButton keyboardButton = new KeyboardButton(BUTTON_ABOUT_CAT_SHELTER);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_MODE_CAT_SHELTER);
        KeyboardButton keyboardButton3 = new KeyboardButton(BUTTON_ADDRESS_CAT_SHELTER);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton, keyboardButton2);

        KeyboardButton keyboardButton4 = new KeyboardButton(BUTTON_SCHEME_CAT_SHELTER);
        KeyboardButton keyboardButton5 = new KeyboardButton(BUTTON_PASS_CAT_SHELTER);
        KeyboardButton keyboardButton6 = new KeyboardButton(BUTTON_PHONE_CAT_SHELTER);
        keyboardMarkup.addRow(keyboardButton5, keyboardButton4);

        keyboardMarkup.addRow(keyboardButton6,keyboardButton3);

        KeyboardButton keyboardButton7 = new KeyboardButton(BUTTON_SAFETY_CAT_SHELTER);
        KeyboardButton keyboardButton8 = new KeyboardButton(BUTTON_VOLUNTEER_CAT_SHELTER);
        keyboardMarkup.addRow(keyboardButton7, keyboardButton8);
        KeyboardButton keyboardButton9 = new KeyboardButton(BUTTON_TO_THE_BEGINNING);
        keyboardMarkup.addRow(keyboardButton9);

        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        SendMessage sendMessage = new SendMessage(chat_id, INFO_STAGE_1_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }
    public SendMessage boardMarkupStageTwoCat(Long chat_id) {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_DATING_RULES);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_LIST_DOCUMENTS);
        KeyboardButton keyboardButton3 = new KeyboardButton(BUTTON_TRANSPORTATION_RECOMMENDATION);
        KeyboardButton keyboardButton4 = new KeyboardButton(BUTTON_LIMITED_ANIMAL);
        KeyboardButton keyboardButton5 = new KeyboardButton(BUTTON_REASONS_REFUSAL);
        KeyboardButton keyboardButton6 = new KeyboardButton(BUTTON_ARRANGEMENT_CAT);
        KeyboardButton keyboardButton7 = new KeyboardButton(BUTTON_ARRANGEMENT_BIG_CAT);
        KeyboardButton keyboardButton8 = new KeyboardButton(BUTTON_PHONE_CAT_SHELTER);
        KeyboardButton keyboardButton9 = new KeyboardButton(BUTTON_VOLUNTEER_CAT_SHELTER);
        KeyboardButton keyboardButton10 = new KeyboardButton(BUTTON_TO_THE_BEGINNING_CAT);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        keyboardMarkup.addRow(keyboardButton3, keyboardButton4);
        keyboardMarkup.addRow(keyboardButton5);
        keyboardMarkup.addRow(keyboardButton6, keyboardButton7);
        keyboardMarkup.addRow(keyboardButton8, keyboardButton9);
        keyboardMarkup.addRow(keyboardButton10);

        keyboardMarkup.resizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(chat_id, INFO_STAGE_2_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }


    public SendMessage boardMarkupStageZeroDog(Long chat_id) {
        KeyboardButton keyboardButton = new KeyboardButton(BUTTON_INFO_DOG_SHELTER);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton);

        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_STAGE_2_DOG);
        keyboardMarkup.addRow(keyboardButton2);
        KeyboardButton keyboardButton3 = new KeyboardButton(BUTTON_REPORT_DOG);
        keyboardMarkup.addRow(keyboardButton3);
        KeyboardButton keyboardButton4 = new KeyboardButton(BUTTON_VOLUNTEER_DOG_SHELTER);
        keyboardMarkup.addRow(keyboardButton4);
        KeyboardButton keyboardButton5 = new KeyboardButton(BUTTON_TO_THE_BEGINNING);
        keyboardMarkup.addRow(keyboardButton5);

        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        SendMessage sendMessage = new SendMessage(chat_id, INFO_STAGE_0_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    public SendMessage boardMarkupStageOneDog(Long chat_id) {
        KeyboardButton keyboardButton = new KeyboardButton(BUTTON_ABOUT_DOG_SHELTER);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_MODE_DOG_SHELTER);
        KeyboardButton keyboardButton3 = new KeyboardButton(BUTTON_ADDRESS_DOG_SHELTER);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton, keyboardButton2);

        KeyboardButton keyboardButton4 = new KeyboardButton(BUTTON_SCHEME_DOG_SHELTER);
        KeyboardButton keyboardButton5 = new KeyboardButton(BUTTON_PASS_DOG_SHELTER);
        KeyboardButton keyboardButton6 = new KeyboardButton(BUTTON_PHONE_DOG_SHELTER);
        keyboardMarkup.addRow(keyboardButton5, keyboardButton4);

        keyboardMarkup.addRow(keyboardButton6, keyboardButton3);

        KeyboardButton keyboardButton7 = new KeyboardButton(BUTTON_SAFETY_DOG_SHELTER);
        KeyboardButton keyboardButton8 = new KeyboardButton(BUTTON_VOLUNTEER_DOG_SHELTER);
        keyboardMarkup.addRow(keyboardButton7, keyboardButton8);
        KeyboardButton keyboardButton9 = new KeyboardButton(BUTTON_TO_THE_BEGINNING);
        keyboardMarkup.addRow(keyboardButton9);

        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        SendMessage sendMessage = new SendMessage(chat_id, INFO_STAGE_1_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    public SendMessage boardMarkupStageTwoDog(Long chat_id) {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_DATING_RULES);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_LIST_DOCUMENTS);
        KeyboardButton keyboardButton3 = new KeyboardButton(BUTTON_TRANSPORTATION_RECOMMENDATION);
        KeyboardButton keyboardButton4 = new KeyboardButton(BUTTON_LIMITED_ANIMAL);
        KeyboardButton keyboardButton5 = new KeyboardButton(BUTTON_REASONS_REFUSAL);
        KeyboardButton keyboardButton6 = new KeyboardButton(BUTTON_ARRANGEMENT_DOG);
        KeyboardButton keyboardButton7 = new KeyboardButton(BUTTON_ARRANGEMENT_BIG_DOG);
        KeyboardButton keyboardButton8 = new KeyboardButton(BUTTON_PHONE_DOG_SHELTER);
        KeyboardButton keyboardButton9 = new KeyboardButton(BUTTON_PHONE_DOG_SHELTER);
        KeyboardButton keyboardButton10 = new KeyboardButton(BUTTON_VOLUNTEER_DOG_SHELTER);
        KeyboardButton keyboardButton11 = new KeyboardButton(BUTTON_ADVICE_DOGHANDLER);
        KeyboardButton keyboardButton12 = new KeyboardButton(BUTTON_RECOMMENDATION_DOGHANDLER);
        KeyboardButton keyboardButton13 = new KeyboardButton(BUTTON_TO_THE_BEGINNING_DOG);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        keyboardMarkup.addRow(keyboardButton3, keyboardButton4);
        keyboardMarkup.addRow(keyboardButton5);
        keyboardMarkup.addRow(keyboardButton6, keyboardButton7);
        keyboardMarkup.addRow(keyboardButton8, keyboardButton9);
        keyboardMarkup.addRow(keyboardButton10, keyboardButton11);
        keyboardMarkup.addRow(keyboardButton12, keyboardButton13);

        keyboardMarkup.resizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(chat_id, INFO_STAGE_2_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    public SendMessage boardMarkupStageThree(Long chat_id) {
        KeyboardButton keyboardButton1 = new KeyboardButton(BUTTON_REPORT_FORM);
        KeyboardButton keyboardButton2 = new KeyboardButton(BUTTON_VOLUNTEER_SHELTER);
        KeyboardButton keyboardButton3 = new KeyboardButton(BUTTON_TO_THE_BEGINNING);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        keyboardMarkup.addRow(keyboardButton3);

        keyboardMarkup.resizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(chat_id, INFO_STAGE_3_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }


}