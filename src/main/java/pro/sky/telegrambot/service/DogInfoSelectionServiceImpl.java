package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

@Component
public class DogInfoSelectionServiceImpl implements IndividualKeyboardButton {

    @Override
    public SendMessage selectionInfo(Long chat_id) {
        String smile_dog = EmojiParser.parseToUnicode(":dog:");

        KeyboardButton keyboardButton1 = new KeyboardButton("Правила знакомства");
        KeyboardButton keyboardButton2 = new KeyboardButton("Список документов");
        KeyboardButton keyboardButton3 = new KeyboardButton("Рекомендации по транспортировке");
        KeyboardButton keyboardButton4 = new KeyboardButton("Обустройство щенка");
        KeyboardButton keyboardButton5 = new KeyboardButton("Обустройство для взрослой собаки");
        KeyboardButton keyboardButton6 = new KeyboardButton("Обустройство для ограниченного");
        KeyboardButton keyboardButton7 = new KeyboardButton("Список причин для отказа");
        KeyboardButton keyboardButton8 = new KeyboardButton("Оставить номер телефона");
        KeyboardButton keyboardButton9 = new KeyboardButton(smile_dog + " Связаться c волонтером");
        KeyboardButton keyboardButton10 = new KeyboardButton(smile_dog + " В начало");

        KeyboardButton keyboardButton11 = new KeyboardButton("Советы кинолога по первичному общению с собакой");
        KeyboardButton keyboardButton12 = new KeyboardButton("Рекомендации по проверенным кинологам");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton1, keyboardButton2);
        keyboardMarkup.addRow(keyboardButton3, keyboardButton4);
        keyboardMarkup.addRow(keyboardButton5, keyboardButton6);
        keyboardMarkup.addRow(keyboardButton7, keyboardButton8);
        keyboardMarkup.addRow(keyboardButton11, keyboardButton12);
        keyboardMarkup.addRow(keyboardButton9, keyboardButton10);

        keyboardMarkup.resizeKeyboard(true);

        String text_bot = "Привет, дорогой усыновитель! Выбери нужную тебе информацию. \nЕсли что могу связать с волонтером.";
        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    public SendMessage puppyArrangementSelection(Long chat_id) {
        String text = """
                1. Место для сна, такое как кровать или корзина.
                2. Миски для еды и воды.
                3. Корм для щенка, соответствующий его возрасту и размеру.
                4. Игрушки для жевания и развлечения.
                5. Подстилка или пеленки для учения щенка делать свои нужды на определенном месте.
                6. Шлейка и поводок для прогулок и тренировок.
                7. Туалет для щенка, если вы планируете держать его внутри дома.
                8. Щетка для груминга и ухода за шерстью.
                9. Дезинфицирующее средство для очистки мест, где щенок делает свои нужды.
                """;
        return new SendMessage(chat_id, text);
    }
    public SendMessage arrangementAdultSelectionDog(Long chat_id) {
        String text = """
                1. Место для сна, такое как кровать или мягкий матрас.
                2. Миски для еды и воды, предпочтительно из нержавеющей стали или керамики.
                3. Корм для взрослой собаки, соответствующий ее возрасту, размеру и потребностям.
                4. Игрушки для игр и развлечения, включая интерактивные игры и игрушки для охоты.
                5. Когтеточка или когтетренировочный материал, чтобы предотвратить повреждение мебели.
                6. Лоток для учения собаки делать свои нужды на определенном месте, если она живет в квартире.
                7. Шлейка и поводок для прогулок и тренировок, если собака привыкла к ним.
                8. Щетка для груминга и ухода за шерстью, чтобы предотвратить образование комков и перхоти.
                9. Дезинфицирующее средство для очистки мест, где собака делает свои нужды, а также для очистки поверхностей и предметов, которые собака может затронуть.
                """;
        return new SendMessage(chat_id, text);
    }
    public SendMessage tipsHandlerInitialCommunicationDog(Long chat_id) {
        String text = """
                Вот основные правила, которые следует соблюдать.
                                
                Весь дрессировочный курс подразделяют на этапы.
                1. Питомца обязательно поощряют.
                2. Хозяин показывает твердость характера во время обучения, ни в коем случае не переходя на агрессивное поведение!
                3. Хозяин понимает характер питомца и находит к нему индивидуальный подход.
                4. Одно занятие с питомцем длится не дольше часа.
                5. Лучше разделить отведенное время на промежутки с небольшими перерывами. Дали команду — собака выполнила — пусть побегает, отвлечется. За одно занятие питомец выполняет все необходимые команды.
                """;
        return new SendMessage(chat_id, text);
    }
    public SendMessage recommendationsForProvenDogHandlers(Long chat_id) {
        String text = """
                Обращайтесь к нам! Лучшим кинологам России!
                
                Группа: vk.com/k9friendly
                Бытовая дрессировка (норматив class), работа с проблемами поведения. Специализация - зооагрессия.
                                
                Little Jack
                группа: https://vk.com/vospitajsobaky
                Выезд по всей Москве.
                Бытовая дрессировка, послушание и коррекция поведения.
                """;
        return new SendMessage(chat_id, text);
    }

}