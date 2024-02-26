package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.Report;

import java.util.Collection;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Report findReportByPhotoNameIdAndCheckReport(String photoNameId, boolean b);

    Report findReportByPhotoNameIdAndGeneralWellBeingAndCheckReport(String photoName, String o, boolean b);

    Collection<Report> findByCheckReport(boolean checkReport);

    Report findReportById(Long id);
}