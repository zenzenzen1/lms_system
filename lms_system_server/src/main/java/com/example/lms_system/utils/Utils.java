package com.example.lms_system.utils;

import java.time.LocalDate;

public class Utils {
    public static boolean checkIsWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() > 5;
    }
}
