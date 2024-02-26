package pro.sky.telegrambot.markup;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Client;
import pro.sky.telegrambot.repository.ClientRepository;


@Component
public class  ShelterKeyboardMarkup {

    private final ClientRepository clientRepository;

    public ShelterKeyboardMarkup(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public SendMessage contactSelection(Long chat_id) {
        return new SendMessage(chat_id, "Введите номер телефона в формате +7 912 345 67 89");
    }
    public SendMessage saveClientContact(Long chat_id, Update update) {
        String firstName = update.message().chat().firstName();
        Client client = clientRepository.findClientByFirstName(firstName);
        SendMessage result;
        if (client == null) {
            Client newClient = new Client();
            newClient.setFirstName(firstName);
            newClient.setPhoneNumber(update.message().text());
            clientRepository.save(newClient);
            result = new SendMessage(chat_id, "Контакт сохранен, мы с Вами обязательно свяжемся");
        } else if (firstName.equals(client.getFirstName()) && client.getPhoneNumber()==null) {
            client.setPhoneNumber(update.message().text());
            clientRepository.save(client);
            result = new SendMessage(chat_id, "Контакт сохранен, мы с Вами обязательно свяжемся");
        } else {
            result = new SendMessage(chat_id, "Мы уже записали Ваш номер телефона");
        }
        return result;
    }
}