package com.example.course_work_3.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@AllArgsConstructor
@Data

public class Socks {
    @Schema(description = "Цвет носков: BLACK, BLUE, GREY, WHITE")
    private Color color;
    @Schema(description = "Размер носков: от 35 до 47. Например: SIZE_35")
    private Size size;
    @Schema(description = "Процент хлопка в модели: от 0 до 100 %")
    @Min(value = 0, message = "Минимальное значение хлопка 0%")
    @Max(value = 100, message = "Значение хлопка не может быть больше 100%")
    private int cotton;
    @Schema(description = "Количество носков")
    @Min(value = 0, message = "Количество не может быть отрицательным")
    private int amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cotton == socks.cotton && size == socks.size && color == socks.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, color, cotton);
    }
}
