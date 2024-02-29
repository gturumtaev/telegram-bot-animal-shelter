package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.Client;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findClientByFirstName(String firstName);

    Optional<Client> findByDateTimeToTookBefore(LocalDateTime dateTimeToTook);
}