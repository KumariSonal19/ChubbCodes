package com.app.work;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendDatesInNovember {
    public static void main(String[] args) {
        int year = 2025; 
        int month = 11; 

        LocalDate date = LocalDate.of(year, month, 1);
        int length = date.lengthOfMonth();

        for (int i = 1; i <= length; i++) {
            LocalDate current = LocalDate.of(year, month, i);
            DayOfWeek day = current.getDayOfWeek();

            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                System.out.println(day + ": " + current);
            }
        }
    }
}

