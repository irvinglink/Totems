package com.github.irvinglink.AmethystLib.utils.nbt.enums;

import lombok.Getter;

public enum DEFAULT_NBT {

    SUPER_SWORD("SuperSword"),
    ;

    @Getter private final String nbtkey;

    DEFAULT_NBT(String nbtkey) {
        this.nbtkey = nbtkey;
    }

}
