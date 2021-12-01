package com.github.irvinglink.AmethystLib.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Used to make page system of any object. Commonly used on chat or inventory system.
 */
public class Paginator<E> {

    public List<E> getPageElements(int page, int perpage_elements, List<E> list) {

        List<E> output = Collections.synchronizedList(new ArrayList<>());

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