package com.github.irvinglink.Totems.utils.chat.hex;

public final class TextColor {

    public final int red;
    public final int green;
    public final int blue;

    public TextColor(String hexCode) {
        final int hexColor = Integer.valueOf(hexCode, 16);
        this.red = hexColor >> 16 & 0xFF;
        this.green = hexColor >> 8 & 0xFF;
        this.blue = hexColor & 0xFF;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getRed() {
        return red;
    }

}
