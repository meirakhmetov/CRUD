package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 400),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 7, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> map = new HashMap<>();
        for(UserMeal meal:meals){
            map.put(meal.getDateTime().toLocalDate(),map.getOrDefault(meal.getDateTime().toLocalDate(),0)+meal.getCalories());
        }
        List<UserMealWithExcess> list = new ArrayList<>();
        for(UserMeal meal:meals){
            if(TimeUtil.isBetweenInclusive(meal.getDateTime().toLocalTime(),startTime,endTime)){
                list.add(new UserMealWithExcess(meal.getDateTime(),meal.getDescription(),meal.getCalories(),
                        caloriesPerDay<map.get(meal.getDateTime().toLocalDate())));
            }
        }
        // TODO return filtered list with excess. Implement by cycles
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> calorisSumByDay = meals.stream()
                .collect(Collectors.groupingBy(um->um.getDateTime().toLocalDate(),Collectors.summingInt(UserMeal::getCalories)));

        List<UserMealWithExcess> list = meals.stream()
                .filter(um->TimeUtil.isBetweenInclusive(um.getDateTime().toLocalTime(),startTime,endTime))
                .map(um->new UserMealWithExcess(um.getDateTime(),um.getDescription(),um.getCalories(),
                        calorisSumByDay.get(um.getDateTime().toLocalDate())>caloriesPerDay))
                .collect(Collectors.toList());

        // TODO Implement by streams
        return list;
    }
}
