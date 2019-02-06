package com.kreezcraft.bonsaicrops.soils;

public enum EnumSoilStat {
    GROW_TIME("grow time"),
    DROP_CHANCE("drop chance");

    private final String humanName;

    EnumSoilStat(String name) {
        this.humanName = name;
    }

    public String getHumanName() {
        return humanName;
    }
}
