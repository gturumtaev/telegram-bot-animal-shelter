package pro.sky.telegrambot.controller;

import com.pengrad.telegrambot.response.SendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Report;
import pro.sky.telegrambot.service.ReportService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/report_tg")
public class ReportController {

    @ExceptionHandler({RuntimeException.class})
    public String handleException(Exception e) {
        return  e.getMessage();
    }

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Поиск отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск отчета",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            })
    @GetMapping("/by-id")
    public ResponseEntity<Optional<Report>> findReportById(@Parameter(description = "Поиск отчета по id")
                                                           @RequestParam Long id) {
        return ResponseEntity.ok(reportService.findReportById(id));
    }

    @Operation(
            summary = "Поиск необработанного отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск необработанного отчета",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            })
    @GetMapping("/not-checked-report")
    public ResponseEntity<Collection<Report>> findNotCheckedReport(@RequestParam boolean checkReport) {
        return ResponseEntity.ok(reportService.findReportByCheckReport(checkReport));
    }

    @Operation(
            summary = "Подтверждение отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Подтверждение отчета",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            })
    @PutMapping("/verified-report")
    public ResponseEntity<String> verifiedReport(@RequestParam Long id,
                                                 @RequestParam boolean checkReport) {
        return ResponseEntity.ok(reportService.acceptanceOfTheReport(id, checkReport));
    }

    @Operation(
            summary = "Отправка сообщений от волонтера об отчете",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отправка сообщений",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            })
    @GetMapping("send-message")
    public SendResponse sendMessage(@RequestParam String firstName,
                                    @RequestParam String message) {
        return reportService.sendMessage(firstName, message);
    }
}