package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.Volunteer;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    Volunteer addVolunteer(Volunteer volunteer);

    List<Volunteer> findVolunteerByShelterId(Long shelterId);

    Optional<Volunteer> findVolunteerById(Long id);

    Volunteer deleteVolunteer(Long id);
}
