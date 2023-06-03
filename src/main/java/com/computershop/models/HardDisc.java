package com.computershop.models;

import com.computershop.models.enums.HardDriveVolumes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HardDisc extends GeneralProperties {

    HardDriveVolumes hardDriveVolumes;

    public HardDisc(@NotNull int seriesNum,
                    @NotBlank String manufacturer,
                    @Positive(message = "Не может быть отрицательным") double cost,
                    @Positive(message = "Не может быть отрицательным") int quantity,
                    @NotNull HardDriveVolumes hardDriveVolumes) {
        super(seriesNum, manufacturer, cost, quantity);
        this.hardDriveVolumes = hardDriveVolumes;
    }

    public HardDisc() {
    }
}
