package org.am.fos.collector;

public enum OrderItemEnum {
    MAIN_COURSE(0),
    DESSERT(1),
    DRINK(2);

    private final int ordinal;

    OrderItemEnum(int ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getOrdinal() {
        return ordinal;
    }
}
