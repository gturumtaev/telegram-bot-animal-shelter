package pro.sky.telegrambot.report;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.IndividualKeyboardButton;

@Component
public class DogReportMarkup implements IndividualKeyboardButton {
    @Override
    public SendMessage selectionInfo(Long chat_id) {

        String smile_dog = EmojiParser.parseToUnicode(":dog:");

        KeyboardButton keyboardButton1 = new KeyboardButton("Форма ежедневного отчета");

        KeyboardButton keyboardButton2 = new KeyboardButton(smile_dog + " Связаться с волонтером");
        KeyboardButton keyboardButton3 = new KeyboardButton(smile_dog + " В начало");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        keyboardMarkup.addRow(keyboardButton3);

        keyboardMarkup.resizeKeyboard(true);

        String text_bot = "Выберите одну из кнопок";
        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }
}