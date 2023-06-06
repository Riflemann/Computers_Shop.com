package com.computershop.controllers;

import com.computershop.models.Desktops;
import com.computershop.models.enums.TypeDesktops;
import com.computershop.service.DesktopsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("desktops")
@Tag(name="Контроллер для работы с настольными компьютерами", description="Управляет действиями пользователей по получению всех комьютеров, получению компьютера по индефикатору, сохранению в базе данных и редактированию сохраненных компьютеров")
public class DesktopsController {

    private final DesktopsService desktopsService;

    public DesktopsController(DesktopsService desktopsService) {
        this.desktopsService = desktopsService;
    }


    @Operation(
            summary = "Получение всех настольных компьютеров из базы данных ",
            description = "Получение всех настольных компьютеров из базы данных",
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
    public ResponseEntity<List<Desktops>> getAllDesktops()  {
        return ResponseEntity.ok(desktopsService.getAll());
    }

    @Operation(
            summary = "Получение настольного компьютера по индефикатору из базы данных ",
            description = "Получение настольного компьютера по индефикатору из базы данных",
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
    public ResponseEntity<Desktops> getDesktopById(@PathVariable String id)  {
        return ResponseEntity.ok(desktopsService.getById(id));
    }

    @Operation(
            summary = "Сохранение настольного компьютера в базе данных ",
            description = "Сохранение настольного компьютера в базе данных ",
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
    public ResponseEntity<Desktops> saveDesktop(@RequestParam(name = "Серийный номер") int series_num,
                                                @RequestParam(name = "Производитель") String manufacturer,
                                                @RequestParam(name = "Цена") double cost,
                                                @RequestParam(name = "Количество") int quantity,
                                                @RequestParam(name = "hardDiscVolume") TypeDesktops type_desktops) {

        return ResponseEntity.ok(desktopsService.save(new Desktops(series_num,
                                                                    manufacturer,
                                                                    cost,
                                                                    quantity,
                                                                    type_desktops)));
    }

    @Operation(
            summary = "Изменение сохраненного в базе данных настольного компьютера по индефикатору ",
            description = "Изменение сохраненного в базе данных настольного компьютера по индефикатору",
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
    public ResponseEntity<Desktops> editDesktop(@RequestBody @Valid Desktops desktops,
                                                String id)  {
        return ResponseEntity.ok(desktopsService.edit(desktops, id));
    }

}
