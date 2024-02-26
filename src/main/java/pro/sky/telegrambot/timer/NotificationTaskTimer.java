package pro.sky.telegrambot.timer;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.repository.ClientRepository;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
public class NotificationTaskTimer {
    private final ClientRepository clientRepository;
    private final TelegramBotUpdatesListener telegramBotUpdatesListener;

    public NotificationTaskTimer(ClientRepository clientRepository, TelegramBotUpdatesListener telegramBotUpdatesListener) {
        this.clientRepository = clientRepository;
        this.telegramBotUpdatesListener = telegramBotUpdatesListener;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void task() {
        LocalDateTime oneDaysAgo = LocalDateTime.now().minusDays(1);
        String message = " «Дорогой усыновитель, мы заметили, что ты заполняешь " +
                "отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее" +
                " к этому занятию. В противном случае волонтеры приюта будут обязаны " +
                "самолично проверять условия содержания животного»";
        clientRepository.findByDateTimeToTookBefore(oneDaysAgo)
                .ifPresent(client -> {
                    if (client.getDateTimeToTook().isBefore(oneDaysAgo)) {
                        telegramBotUpdatesListener.executeSendMessage(client.getChatId(), message);
                    }
                });
    }
}