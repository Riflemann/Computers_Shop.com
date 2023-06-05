package com.computershop.models.enums;

public enum Diagonal {
    DIAGONAL_13(13),
    DIAGONAL_14(14),
    DIAGONAL_15(15),
    DIAGONAL_17(17);

    final int diagonal;
    Diagonal(int diagonal) {
        this.diagonal = diagonal;
    }
}
