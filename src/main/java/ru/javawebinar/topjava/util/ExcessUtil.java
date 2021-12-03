package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ExcessUtil {
    public static boolean isExcess(List<UserMeal> meals, LocalDate date, int caloriesPerDay) {
        int fullCaloriesPerDay = 0;
        for (UserMeal meal : meals) {
            if (meal.getDateTime().toLocalDate().equals(date)) {
                fullCaloriesPerDay =fullCaloriesPerDay+ meal.getCalories();
            }
        }
        if (fullCaloriesPerDay > caloriesPerDay) {
            return true;
        } else {
            return false;
        }
    }

}
