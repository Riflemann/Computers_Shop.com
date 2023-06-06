package com.computershop.controllers;

import com.computershop.models.Monitor;
import com.computershop.models.enums.Diagonal;
import com.computershop.service.MonitorsService;
import com.computershop.service.MonitorsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("monitors")
@Tag(name="Контроллер для работы с мониторами ", description="Управляет действиями пользователей по получению всех ноутбуков, получению по индефикатору, сохранению в базе данных и редактированию сохраненных в базе данных")
public class MonitorsController {

    private final MonitorsService monitorsService;

    public MonitorsController(MonitorsService monitorsService) {
        this.monitorsService = monitorsService;
    }


    @Operation(
            summary = "Получение всех мониторов из базы данных ",
            description = "Получение всех мониторов из базы данных",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Monitor>> getAllDesktops()  {
        return ResponseEntity.ok(monitorsService.getAll());
    }

    @Operation(
            summary = "Получение монитора по индефикатору из базы данных ",
            description = "Получение монитора по индефикатору из базы данных",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping(value = "{id}")
    public ResponseEntity<Monitor> getDesktopById(@PathVariable String id)  {
        return ResponseEntity.ok(monitorsService.getById(id));
    }

    @Operation(
            summary = "Сохранение монитора в базе данных ",
            description = "Сохранение монитора в базе данных",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping(value = "save")
    public ResponseEntity<Monitor> saveDesktop(@RequestParam(name = "Серийный номер") int series_num,
                                              @RequestParam(name = "Производитель") String manufacturer,
                                              @RequestParam(name = "Цена") double cost,
                                              @RequestParam(name = "Количество") int quantity,
                                              @RequestParam(name = "Диагональ") Diagonal diagonal) {

        return ResponseEntity.ok(monitorsService.save(new Monitor(series_num,
                manufacturer,
                cost,
                quantity,
                diagonal)));
    }

    @Operation(
            summary = "Изменение сохраненного в базе данных монитора по индефикатору ",
            description = "Изменение сохраненного в базе данных монитора по индефикатору",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )
    @PutMapping(value = "edit/{id}")
    public ResponseEntity<Monitor> editDesktop(@RequestBody @Valid Monitor monitor,
                                              String id)  {
        return ResponseEntity.ok(monitorsService.edit(monitor, id));
    }
    
}
