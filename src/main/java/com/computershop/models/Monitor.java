package com.computershop.models;

import com.computershop.models.enums.Diagonal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Monitor extends GeneralProperties {

    private Diagonal diagonal;

    public Monitor(@NotNull int seriesNum,
                   @NotBlank String manufacturer,
                   @Positive(message = "Не может быть отрицательным") double cost,
                   @Positive(message = "Не может быть отрицательным") int quantity,
                   @NotNull Diagonal diagonal) {
        super(seriesNum, manufacturer, cost, quantity);
        this.diagonal = diagonal;
    }

    public Monitor() {
    }
}
