package pro.sky.telegrambot.service.Impl;

import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.ClientRepository;
import pro.sky.telegrambot.repository.DrivingDirectionsRepository;
import pro.sky.telegrambot.repository.ShelterRepository;
import pro.sky.telegrambot.service.ShelterService;
import pro.sky.telegrambot.service.VolunteerService;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Objects;


@Component
public class ShelterCatServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;
    private final DrivingDirectionsRepository drivingDirectionsRepository;
    private final VolunteerService volunteerService;

    @Autowired
    public ShelterCatServiceImpl(ShelterRepository shelterRepository, DrivingDirectionsRepository drivingDirectionsRepository, VolunteerService volunteerService, ClientRepository clientRepository) {
        this.shelterRepository = shelterRepository;
        this.drivingDirectionsRepository = drivingDirectionsRepository;
        this.volunteerService = volunteerService;
    }

    @Override
    public SendMessage getWorkScheduleFromDB(Long chat_id) {
        String workSchedule = shelterRepository.findById(1L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getWorkSchedule();
        return new SendMessage(chat_id, workSchedule);
    }

    @Override
    public SendMessage getAddressFromDB(Long chat_id) {
        String address = shelterRepository.findById(1L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getAddress();
        return new SendMessage(chat_id, address);
    }
    @Override
    public SendPhoto getDrivingDirections(Long chat_id) {
        String url = Objects.requireNonNull(drivingDirectionsRepository.findById(1L).orElse(null)).getFilePath();
        url = url.substring(0, url.length() - 1);
        return new SendPhoto(chat_id, url);
    }

    @Override
    public SendMessage getShelterPhoneNumberSecurityFromDB(Long chat_id) {
        String securityPhone = shelterRepository.findById(1L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getSecurity();
        return new SendMessage(chat_id, "Для оформления пропуска на территорию приюта позвоните по номеру телефона - " + securityPhone);
    }

    @Override
    public SendMessage getShelterSafetyPrecautionsSecurityFromDB(Long chat_id) {
        String safetyPrecautions = shelterRepository.findById(1L).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getSafetyPrecautions();
        return new SendMessage(chat_id, safetyPrecautions);
    }

    @Override
    public SendMessage shelterStory(Long chat_id) {

        return new SendMessage(chat_id, "Наш приют занимается поиском новых хозяев для бездомных кошек");
    }

    @Override
    public SendMessage getVolunteersShelter(Long chat_id) {
        List<Volunteer> volunteersDog = volunteerService.findVolunteerByShelterId(1L);
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