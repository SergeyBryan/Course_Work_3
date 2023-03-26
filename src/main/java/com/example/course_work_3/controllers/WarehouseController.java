package com.example.course_work_3.controllers;

import com.example.course_work_3.model.Socks;
import com.example.course_work_3.services.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Tag(name = "Учёт товаров на складе интернет-магазина носков", description = "С помощью данного приложения можно учитывать и автоматизировать учёт товаров на складе.")
@RestController
@RequestMapping("/socks")
public class WarehouseController {
    private WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }


    @GetMapping()
    @Operation(summary = "Возвращает общее количество носков на складе", description = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "запрос выполнен, результат в теле ответа в виде строкового представления целого числа"),
            @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")})
    public ResponseEntity<?> getSetSocks(@RequestParam(defaultValue = "100") @Parameter(description = "Максимальное значение '100'") @Min(0) @Max(100) int cottonMax, @RequestParam(defaultValue = "0", required = false) @Parameter(description = "Минимальное значение '0'") @Min(value = 0) @Max(value = 100) int cottonMin, @RequestParam @Parameter(description = "BLACK, BLUE, GREY, WHITE") String color, @RequestParam(defaultValue = "0", required = false) @Parameter(description = "Размер с 35 по 47, если не указан, то выведет информацию по всем размерам по цвету") int size) {
        if ((warehouseService.getSocks(color, cottonMin, cottonMax, size)).size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("По указанным критериям ничего не найдено");
        } else {
            return ResponseEntity.ok(warehouseService.getSocks(color, cottonMin, cottonMax, size));
        }
    }

    @DeleteMapping()
    @Operation(summary = "Регистрирует списание испорченных (бракованных) носков.", description = "Параметры запроса передаются в теле запроса в виде JSON-объекта")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "запрос выполнен, товар списан со склада"),
            @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = " произошла ошибка, не зависящая от вызывающей стороны")})
    public ResponseEntity<String> deleteSocks(@Valid @RequestBody Socks socks) {
        warehouseService.deleteSetSocks(socks);
        return ResponseEntity.ok("Списаны: " + socks);
    }

    @PostMapping()
    @Operation(summary = "Регистрирует приход товара на склад.", description = "Параметры запроса передаются в теле запроса в виде JSON-объекта")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "удалось добавить приход"),
            @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")})
    public ResponseEntity<String> addSocks(@Valid @RequestBody Socks socks) {
        warehouseService.addSetSocks(socks);
        return ResponseEntity.ok("Добавлено: " + socks);
    }

    @PutMapping
    @Operation(summary = "Регистрирует отпуск носков со склада.", description = "Параметры запроса передаются в теле запроса в виде JSON-объекта")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = " удалось произвести отпуск носков со склада"),
            @ApiResponse(responseCode = "400", description = "товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"),
            @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")})
    public ResponseEntity<String> editSocks(@Valid @RequestBody Socks socks) {
        warehouseService.editSocks(socks);
        return ResponseEntity.ok("Уехало " + socks.getAmount() + " носков");
    }
}
