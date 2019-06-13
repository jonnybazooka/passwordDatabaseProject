package org.sda.database.tables;

public enum Rot {

    ROT13 ("ROT13"),
    ROT18 ("ROT18"),
    ROT47 ("ROT47");

    private String rotType;

    Rot(String rotType) {
    }

    public String getRotType() {
        return rotType;
    }
}
