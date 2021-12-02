package com.github.irvinglink.AmethystLib.utils.nbt.enums;

import lombok.Getter;

public enum DEFAULT_NBT {

    SUPER_SWORD("SuperSword"),
    ;

    @Getter
    private final String nbtId;

    DEFAULT_NBT(String nbtId) {
        this.nbtId = nbtId;
    }

}
