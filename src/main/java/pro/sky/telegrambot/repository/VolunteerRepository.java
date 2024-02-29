package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Volunteer;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    List<Volunteer> findByShelterId (Long shelterId);
}
