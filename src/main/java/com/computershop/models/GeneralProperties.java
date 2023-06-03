package com.computershop.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class GeneralProperties {

    @NotNull
    private int seriesNum;
    @NotBlank
    private String manufacturer;
    @Positive(message = "Не может быть отрицательным")
    private double cost;
    @Positive(message = "Не может быть отрицательным")
    private int quantity;

    @Override
    public String toString() {
        return "seriesNum=" + seriesNum +
                ", manufacturer='" + manufacturer + '\'' +
                ", cost=" + cost +
                ", quantity=" + quantity +
                '}';
    }
}
