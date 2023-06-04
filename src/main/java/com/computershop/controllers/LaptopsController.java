package com.computershop.controllers;

import com.computershop.models.Laptop;
import com.computershop.models.enums.Diagonal;
import com.computershop.service.LaptopsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("laptops")
@Tag(name="Контроллер для работы с ноутбуками ", description="Управляет действиями пользователей по получению всех ноутбуков, получению по индефикатору, сохранению в базе данных и редактированию сохраненных в базе данных")
public class LaptopsController {

    private final LaptopsService laptopsService;

    public LaptopsController(LaptopsService laptopsService) {
        this.laptopsService = laptopsService;
    }


    @Operation(
            summary = "Получение всех ноутбуков из базы данных ",
            description = "Получение всех ноутбуков из базы данных",
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
    public ResponseEntity<List<Laptop>> getAllDesktops()  {
        return ResponseEntity.ok(laptopsService.getAll());
    }

    @Operation(
            summary = "Получение ноутбука по индефикатору из базы данных ",
            description = "Получение ноутбука по индефикатору из базы данных",
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
    public ResponseEntity<Laptop> getDesktopById(@PathVariable String id)  {
        return ResponseEntity.ok(laptopsService.getById(id));
    }

    @Operation(
            summary = "Сохранение ноутбука в базе данных ",
            description = "Сохранение ноутбука в базе данных",
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
    public ResponseEntity<Laptop> saveDesktop(@RequestParam(name = "Серийный номер") int series_num,
                                                @RequestParam(name = "Производитель") String manufacturer,
                                                @RequestParam(name = "Цена") double cost,
                                                @RequestParam(name = "Количество") int quantity,
                                                @RequestParam(name = "Диагональ") Diagonal diagonal) {

        return ResponseEntity.ok(laptopsService.save(new Laptop(series_num,
                manufacturer,
                cost,
                quantity,
                diagonal)));
    }

    @Operation(
            summary = "Изменение сохраненного в базе данных ноутбука по индефикатору ",
            description = "Изменение сохраненного в базе данных ноутбука по индефикатору",
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
    public ResponseEntity<Laptop> editDesktop(@RequestBody @Valid Laptop laptop,
                                                String id)  {
        return ResponseEntity.ok(laptopsService.edit(laptop, id));
    }
}
