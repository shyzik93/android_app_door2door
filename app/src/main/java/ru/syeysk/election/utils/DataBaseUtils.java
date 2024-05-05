package ru.syeysk.election.utils;

import java.util.UUID;

public class DataBaseUtils {

    public static String getFieldCreateDate_Create() {
        return "date_create DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP";
    }
    public static String getFieldCreateDate_name() {
        return "date_create";
    }

    public static String getFieldModifyDate_Create() {
        return "date_modify DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP";
    }
    public static String getFieldModifyDate_name() {
        return "date_modify";
    }

    // TODO: INDEX
    public static String getFieldUUID_Create() {
        return "uuid VARCHAR(36) NOT NULL"; // 32 для 16 байт и 4 для дефисов между группами
    }
    public static String getFieldUUID_name() {
        return "uuid";
    }
    public static String getFieldUUID_value() {
        return UUID.randomUUID().toString();
    }



}
