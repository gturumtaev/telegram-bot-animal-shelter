package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.response.SendResponse;
import pro.sky.telegrambot.model.Report;

import java.util.Collection;
import java.util.Optional;

public interface ReportService {
    Optional<Report> findReportById(Long id);

    Collection<Report> findReportByCheckReport(boolean checkReport);

    String acceptanceOfTheReport(Long id, boolean checkReport);

    SendResponse sendMessage(String firstName, String message);
}
