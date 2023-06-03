package com.computershop.models;

import com.computershop.models.enums.TypeDesktops;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Desktop extends GeneralProperties {

    private TypeDesktops typeDesktops;

    public Desktop(@NotNull int seriesNum,
                   @NotBlank String manufacturer,
                   @Positive(message = "Не может быть отрицательным") double cost,
                   @Positive(message = "Не может быть отрицательным") int quantity,
                   @NotNull TypeDesktops typeDesktops) {
        super(seriesNum, manufacturer, cost, quantity);
        this.typeDesktops = typeDesktops;
    }

    public Desktop() {
    }

    @Override
    public String toString() {
        return "Desktop{" +
                "typeDesktops=" + typeDesktops +
                super.toString() +
                '}';
    }
}
