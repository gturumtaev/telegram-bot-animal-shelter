package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Shelter;

import java.util.Optional;
@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}