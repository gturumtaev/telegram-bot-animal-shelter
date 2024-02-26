package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

@Component
public class CatInfoSelectionServiceImpl implements CatInfoSelectionService{
    @Override
    public SendMessage selectionInfo(Long chat_id) {
        String smile_cat = EmojiParser.parseToUnicode(":cat2:");

        KeyboardButton keyboardButton1 = new KeyboardButton("Правила знакомства");
        KeyboardButton keyboardButton2 = new KeyboardButton("Список документов");
        KeyboardButton keyboardButton3 = new KeyboardButton("Рекомендации по транспортировке");
        KeyboardButton keyboardButton4 = new KeyboardButton("Обустройство котенка");
        KeyboardButton keyboardButton5 = new KeyboardButton("Обустройство для взрослого кота");
        KeyboardButton keyboardButton6 = new KeyboardButton("Обустройство для ограниченного");
        KeyboardButton keyboardButton7 = new KeyboardButton("Список причин для отказа");
        KeyboardButton keyboardButton8 = new KeyboardButton("Оставить номер телефона");
        KeyboardButton keyboardButton9 = new KeyboardButton(smile_cat + " Связаться с волонтером");
        KeyboardButton keyboardButton10 = new KeyboardButton(smile_cat+ " В начало");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        keyboardMarkup.addRow(keyboardButton3, keyboardButton4);
        keyboardMarkup.addRow(keyboardButton5, keyboardButton6);
        keyboardMarkup.addRow(keyboardButton7, keyboardButton8);
        keyboardMarkup.addRow(keyboardButton9, keyboardButton10);

        keyboardMarkup.resizeKeyboard(true);

        String text_bot = "Привет, дорогой усыновитель! Выбери нужную тебе информацию. \nЕсли что могу связать с волонтером.";
        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    @Override
    public SendMessage kittenArrangementSelection(Long chat_id) {
        String text = """
                1. Место для сна, такое как кошачий домик или мягкая подушка.
                2. Миски для еды и воды.
                3. Корм для котенка, соответствующий его возрасту и размеру.
                4. Игрушки для игр и развлечения.
                5. Когтеточка или когтетренировочный материал.
                6. Подстилка или лоток для учения котенка делать свои нужды на определенном месте.
                7. Шлейка и поводок для прогулок и тренировок.
                """;
        return new SendMessage(chat_id, text);
    }

    @Override
    public SendMessage arrangementAdultSelectionCat(Long chat_id) {
        String text = """
                1. Место для сна, такое как кровать или мягкий матрас.
                2. Миски для еды и воды, предпочтительно из нержавеющей стали или керамики.
                3. Корм для взрослого кота, соответствующий его возрасту, размеру и потребностям.
                4. Игрушки для игр и развлечения, включая интерактивные игры и игрушки для охоты.
                5. Когтеточка или когтетренировочный материал, чтобы предотвратить повреждение мебели.
                6. Лоток для учения кота делать свои нужды на определенном месте, предпочтительно с запахоней или без запаха.
                7. Шлейка и поводок для прогулок и тренировок, если кот привык к ним.
                8. Щетка для груминга и ухода за шерстью, чтобы предотвратить образование комков и перхоти.
                """;
        return new SendMessage(chat_id, text);
    }
}
