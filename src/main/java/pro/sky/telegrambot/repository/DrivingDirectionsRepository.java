package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.DrivingDirections;

@Repository
public interface DrivingDirectionsRepository extends JpaRepository<DrivingDirections,Long> {
}