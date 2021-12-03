package com.github.irvinglink.AmethystLib.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Utils {

    public UUID getOfflineUUID(String name) {
        return UUID.nameUUIDFromBytes(String.format("OfflinePlayer:%s", new Object[]{name}).getBytes(StandardCharsets.UTF_8));
    }

    public Object[] removeArrayElement(Object[] array, int index) {

        if (array == null || index < 0 || index >= array.length) return array;

        Object[] newArray = new String[array.length - 1];

        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) continue;

            newArray[k++] = array[i];

        }
        return newArray;
    }

    @Deprecated
    /**
     * Used to make a page system such as chat or inventories, etc.
     * Deprecated: Moved to {@link Paginator}
     */
    public List<String> getPageElements(int page, int perpage_elements, List<String> list) {
        List<String> output = Collections.synchronizedList(new ArrayList<>());

        final int temp_perpage_elements = Math.min(list.size(), perpage_elements);

        final int totalPages = (int) (Math.ceil((double) list.size() / (double) temp_perpage_elements));

        int currentPage = (page == 0) ? 1 : page;

        if (currentPage > totalPages) currentPage = totalPages;

        int highestNumber = currentPage * temp_perpage_elements;
        int lowestNumber = highestNumber - temp_perpage_elements;

        for (int i = lowestNumber; i < highestNumber; i++) if (i < list.size()) output.add(list.get(i));

        return output;
    }


}
