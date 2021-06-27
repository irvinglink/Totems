package com.github.irvinglink.AmethystLib.utils.chat;



import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexManager {

    private final Pattern hexPattern = Pattern.compile("<#[a-zA-z0-9]{6}>");
    private final Pattern gradientPattern = Pattern.compile("<\\$#[a-zA-z0-9]{6}>");

    /*
        CHECK LIBRARY VERSION THIS WILL THROW ERROR IF THE SPIGOT/PAPER VERSION IS LOWER THAN 1.16
     */
    public String hexMessage(String textToTranslate) {

        if (textToTranslate == null) return "";

        Matcher matcher = hexPattern.matcher(textToTranslate);

        int hexAmount = 0;
        if (matcher.find()) {
            matcher.region(matcher.end() - 1, textToTranslate.length());
            hexAmount++;
        }

        int startIndex = 0;

        for (int hexIndex = 0; hexIndex < hexAmount; hexIndex++) {

            int msgIndex = textToTranslate.indexOf("<#", startIndex) + 1;
            String hex = textToTranslate.substring(msgIndex, msgIndex + 7);

            textToTranslate = textToTranslate.replace("<" + hex + ">", ChatColor.of(hex) + "");

        }

        return ChatColor.translateAlternateColorCodes('&', textToTranslate);
    }

}
