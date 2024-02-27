package pro.sky.telegrambot.service.Impl;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.CatInfoSelectionService;

import static pro.sky.telegrambot.constans.Constans.*;

@Component
public class CatInfoSelectionServiceImpl implements CatInfoSelectionService {


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
