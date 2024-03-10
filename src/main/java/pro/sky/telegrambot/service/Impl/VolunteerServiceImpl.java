package pro.sky.telegrambot.service.Impl;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.List;
import java.util.Optional;
@Service
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;
    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Добавление волонтёра в БД.
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param volunteer Добавляемый волонтер
     * @return Добавленного волонтёра
     */
    public Volunteer addVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Поиск волонтёра по его идентификатору приюта в БД.
     * Используется метод репозитория {@link VolunteerRepository#findByShelterId(Long)}
     * @param shelterId Идентификатор нужного приюта.
     * @return Найденного волонтёр.
     */
    public List<Volunteer> findVolunteerByShelterId(Long shelterId) {
        return volunteerRepository.findByShelterId(shelterId);
    }

    /**
     * Поиск волонтёра по его идентификатору в БД.
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param id Идентификатор искомого волонтёра.
     * @return Найденного волонтёр.
     */
    public Optional<Volunteer> findVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }

    /**
     * Удаление волонтёра из БД.
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#delete(Object)}
     * @param id Удаляемого волонтёра.
     * @return Удаленного волонтёра.
     */
    public Volunteer deleteVolunteer(Long id) {
        Volunteer volunteerForDelete = volunteerRepository.findById(id).get();
        volunteerRepository.deleteById(id);
        return volunteerForDelete;
    }
}
