package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.service.VolunteerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(
            summary = "Добавление волонтёра в приют",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавление волонтёра",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            })
    @PostMapping
    public ResponseEntity<Volunteer> addVolunteer (@Parameter(description = "Добавление волонтёра")
                                                   @RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.addVolunteer(volunteer));
    }

    @Operation(
            summary = "Поиск волонтёра по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск волонтёра",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            })
    @GetMapping("/by-id")
    public ResponseEntity<Optional<Volunteer>> getVolunteerById(@Parameter(description = "Поиск волонтёра по id")
                                                                @RequestParam Long id) {
        return ResponseEntity.ok(volunteerService.findVolunteerById(id));
    }

    @Operation(
            summary = "Поиск волонтёров по id приюта",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск волонтёров",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            })

    @GetMapping("/by-shelterId")
    public ResponseEntity<List<Volunteer>> getByShelterId(@Parameter(description = "Поиск волонтёра по id приюта")
                                                          @RequestParam Long shelterId) {
        return ResponseEntity.ok(volunteerService.findVolunteerByShelterId(shelterId));
    }

    @Operation(
            summary = "Удаление волонтёра из приюта",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление волонтёра",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class)
                            )
                    )
            })
    @DeleteMapping
    public ResponseEntity<Volunteer> deleteVolunteerById(@Parameter(description = "Удаление волонтёра")
                                                         @RequestParam Long id) {
        return ResponseEntity.ok(volunteerService.deleteVolunteer(id));
    }
}