package org.am.fos.collector;

import java.util.Arrays;

public enum CuisineEnum {
    POLISH("P"),
    MEXICAN("M"),
    ITALIAN("I");

    private final String ordinal;

    CuisineEnum(String ordinal) {
        this.ordinal = ordinal;
    }

    public String getOrdinal() {
        return ordinal;
    }

    public static CuisineEnum getByOrdinal(String ordinal) {
        return Arrays.stream(values())
                .filter(e -> e.getOrdinal().equals(ordinal))
                .findFirst()
                .orElse(null);
    }
}
