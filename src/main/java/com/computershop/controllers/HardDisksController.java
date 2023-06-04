package com.computershop.controllers;

import com.computershop.models.HardDisc;
import com.computershop.models.enums.HardDriveVolumes;
import com.computershop.service.HardDiscsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hard-drive")
@Tag(name="Контроллер для работы с жесткими дисками ", description="Управляет действиями пользователей по получению всех жестких дисков, получению индефикатору, сохранению в базе данных и редактированию сохраненных в базе данных")
public class HardDisksController {

    private final HardDiscsService hardDiscsService;

    public HardDisksController(HardDiscsService hardDiscsService) {
        this.hardDiscsService = hardDiscsService;
    }


    @Operation(
            summary = "Получение всех жестких дисков из базы данных ",
            description = "Получение всех жестких дисков из базы данных",
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
    public ResponseEntity<List<HardDisc>> getAllDesktops()  {
        return ResponseEntity.ok(hardDiscsService.getAll());
    }

    @Operation(
            summary = "Получение жесткого диска по индефикатору из базы данных ",
            description = "Получение жесткого диска по индефикатору из базы данных",
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
    public ResponseEntity<HardDisc> getDesktopById(@PathVariable String id)  {
        return ResponseEntity.ok(hardDiscsService.getById(id));
    }

    @Operation(
            summary = "Сохранение жесткого диска в базе данных ",
            description = "Сохранение жесткого диска в базе данных",
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
    @GetMapping(value = "save")
    public ResponseEntity<HardDisc> saveDesktop(@RequestParam(name = "Серийный номер") int series_num,
                                                @RequestParam(name = "Производитель") String manufacturer,
                                                @RequestParam(name = "Цена") double cost,
                                                @RequestParam(name = "Количество") int quantity,
                                                @RequestParam(name = "Объем жесткого диска") HardDriveVolumes hardDiscVolume) {

        return ResponseEntity.ok(hardDiscsService.save(new HardDisc(series_num,
                manufacturer,
                cost,
                quantity,
                hardDiscVolume)));
    }

    @Operation(
            summary = "Изменение сохраненного в базе данных жесткого диска по индефикатору ",
            description = "Изменение сохраненного в базе данных жесткого диска по индефикатору",
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
    @GetMapping(value = "edit/{id}")
    public ResponseEntity<HardDisc> editDesktop(@RequestBody @Valid HardDisc hardDisc,
                                                String id)  {
        return ResponseEntity.ok(hardDiscsService.edit(hardDisc, id));
    }
}
