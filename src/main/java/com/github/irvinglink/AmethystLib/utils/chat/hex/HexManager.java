package com.github.irvinglink.AmethystLib.utils.chat.hex;


import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexManager {

    @Getter
    public static final HexManager Instance = new HexManager();

    private final Pattern hexPattern = Pattern.compile("#[a-zA-z0-9]{6}");
    private final Pattern gradientPattern = Pattern.compile("<\\$#[0-9a-fA-F]{6}>[^<]*<\\$#[0-9a-fA-F]{6}>");
    private final Pattern fix2 = Pattern.compile("\\{#[0-9a-fA-F]{6}\\}");
    private final Pattern fix3 = Pattern.compile("<#[0-9a-fA-F]{6}>");
    private final Pattern fix4 = Pattern.compile("\\&x[\\&0-9a-fA-F]{12}");


    private HexManager() {
    }


    private String toChatColor(String hexCode) {
        final StringBuilder magic = new StringBuilder("§x");
        for (char c : hexCode.substring(1).toCharArray()) {
            magic.append('§').append(c);
        }
        return magic.toString();
    }

    private String toHexString(int red, int green, int blue) {
        final StringBuilder sb = new StringBuilder(Integer.toHexString((red << 16) + (green << 8) + blue));
        while (sb.length() < 6) sb.insert(0, "0");
        return sb.toString();
    }

    private String applyFormats(String textInput) {
        textInput = fixFormat1(textInput);
        textInput = fixFormat2(textInput);
        textInput = fixFormat3(textInput);
        textInput = fixFormat4(textInput);

        textInput = setGradient1(textInput);

        return textInput;
    }

    public String toChatColorString(String textInput) {
        textInput = applyFormats(textInput);
        final Matcher m = hexPattern.matcher(textInput);

        while (m.find()) {
            final String hexCode = m.group();
            textInput = textInput.replace(hexCode, toChatColor(hexCode));
        }

        return textInput;
    }

    //&#RRGGBB
    private String fixFormat1(String text) {
        return text.replace("&#", "#");
    }

    //{#RRGGBB}
    private String fixFormat2(String input) {
        final Matcher m = fix2.matcher(input);

        while (m.find()) {
            final String hexcode = m.group();
            input = input.replace(hexcode, "#" + hexcode.substring(2, 8));
        }

        return input;
    }

    //<#RRGGBB>
    private String fixFormat3(String input) {
        final Matcher m = fix3.matcher(input);

        while (m.find()) {
            final String hexcode = m.group();
            input = input.replace(hexcode, "#" + hexcode.substring(2, 8));
        }

        return input;
    }

    //&x&R&R&G&G&B&B
    private String fixFormat4(String text) {
        text = text.replace('\u00a7', '&');
        final Matcher m = fix4.matcher(text);

        while (m.find()) {
            final String hexcode = m.group();
            final StringBuilder fixed = new StringBuilder("#");
            fixed.append(hexcode.charAt(3));
            fixed.append(hexcode.charAt(5));
            fixed.append(hexcode.charAt(7));
            fixed.append(hexcode.charAt(9));
            fixed.append(hexcode.charAt(11));
            fixed.append(hexcode.charAt(13));
            text = text.replace(hexcode, fixed);
        }

        return text;
    }

    //<$#RRGGBB>Text<$#RRGGBB>
    private String setGradient1(String input) {
        final Matcher m = gradientPattern.matcher(input);

        while (m.find()) {
            final String format = m.group();

            final TextColor start = new TextColor(format.substring(3, 9));
            final String message = format.substring(10, format.length() - 10);
            final TextColor end = new TextColor(format.substring(format.length() - 7, format.length() - 1));

            input = input.replace(format, asGradient(start, message, end));
        }

        return input;
    }

    private String asGradient(TextColor start, String text, TextColor end) {
        final StringBuilder sb = new StringBuilder();
        final int length = text.length();

        for (int i = 0; i < length; i++) {
            final int red = (int) (start.red + ((float) (end.red - start.red)) / (length - 1) * i);
            final int green = (int) (start.green + ((float) (end.green - start.green)) / (length - 1) * i);
            final int blue = (int) (start.blue + ((float) (end.blue - start.blue)) / (length - 1) * i);
            sb.append("#").append(toHexString(red, green, blue)).append(text.charAt(i));
        }

        return sb.toString();
    }


}
