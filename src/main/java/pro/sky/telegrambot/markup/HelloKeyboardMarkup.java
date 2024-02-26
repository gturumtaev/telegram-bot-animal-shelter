package pro.sky.telegrambot.markup;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

import static pro.sky.telegrambot.constans.Constans.*;

@Component
public class HelloKeyboardMarkup {

    public static SendMessage boardMarkupCatAndDog(Long chat_id) {
        KeyboardButton keyboardButtonCat = new KeyboardButton(BUTTON_CAT_SHELTER);
        KeyboardButton keyboardButtonDog = new KeyboardButton(BUTTON_DOG_SHELTER);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButtonCat);

        keyboardMarkup.addRow(keyboardButtonDog);

        keyboardMarkup.resizeKeyboard(true);

        SendMessage sendMessage = new SendMessage(chat_id, WELLCOME_MESSAGE);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }
}