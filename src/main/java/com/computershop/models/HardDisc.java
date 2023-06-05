package com.computershop.models;

import com.computershop.models.enums.HardDriveVolumes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class HardDisc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "series_num")
    private int series_num;

    @NotBlank
    private String manufacturer;

    @Positive(message = "Не может быть отрицательным")
    private double cost;

    @Positive(message = "Не может быть отрицательным")
    private int quantity;

    HardDriveVolumes hardDriveVolumes;

    public HardDisc(@NotNull int series_num,
                    @NotBlank String manufacturer,
                    @Positive(message = "Не может быть отрицательным") double cost,
                    @Positive(message = "Не может быть отрицательным") int quantity,
                    @NotNull HardDriveVolumes hardDriveVolumes) {
        this.series_num = series_num;
        this.manufacturer = manufacturer;
        this.cost = cost;
        this.quantity = quantity;
        this.hardDriveVolumes = hardDriveVolumes;
    }

    public HardDisc() {
    }
}
