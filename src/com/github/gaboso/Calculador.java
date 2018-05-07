package com.github.gaboso;

import com.github.gaboso.enumeration.State;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.github.gaboso.enumeration.State.LUNCH;
import static com.github.gaboso.enumeration.State.START_OF_DAY;

public class Calculador {

    private static final int DURATION_OF_DAY = 528;
    private static final int START = 0;
    private static final int START_LUNCH = 1;
    private static final int END_LUNCH = 2;
    private static final int END = 3;

    private HashMap<String, List<String>> map = new HashMap<>();

    public static void main(String[] args) {
        Calculador calculador = new Calculador();

        int quantityOfDays = 20;

        for (int i = 0; i < quantityOfDays; i++) {
            List<String> durationsOfDay = calculador.getDurationsOfDay();
            calculador.map.put((i + 1) + "", durationsOfDay);
        }

        for (int i = 0; i < quantityOfDays; i++) {
            String day = (i + 1) + "";
            List<String> durations = calculador.map.get(day);
            System.out.println("Dia: " + calculador.format(i + 1) + "| " +
                    durations.get(START) + " - " +
                    durations.get(START_LUNCH) + " - " +
                    durations.get(END_LUNCH) + " - " +
                    durations.get(END));
        }

    }

    private List<String> getDurationsOfDay() {
        int startMinutes = getMinutes();
        int startHour = getStartHour(startMinutes, START_OF_DAY);
        int startLunchMinutes = getMinutes();
        int startLunchHour = getStartHour(startLunchMinutes, LUNCH);
        int endLunchHour = startLunchHour + 1;

        Date startDate = parseToDate(startHour, startMinutes);
        Date startLunchDate = parseToDate(startLunchHour, startLunchMinutes);
        Date endLunchDate = parseToDate(endLunchHour, startLunchMinutes);
        Date endDate = calculateEndDate(startDate, startLunchDate, endLunchDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endMinutes = calendar.get(Calendar.MINUTE);

        List<String> durations = new ArrayList<>();
        durations.add(format(startHour) + ":" + format(startMinutes));
        durations.add(format(startLunchHour) + ":" + format(startLunchMinutes));
        durations.add(format(endLunchHour) + ":" + format(startLunchMinutes));
        durations.add(format(endHour) + ":" + format(endMinutes));

        return durations;
    }

    private String format(int number) {
        return String.format("%02d", number);
    }

    private Date calculateEndDate(Date startDate, Date startLunchDate, Date endLunchDate) {
        long durationUntilLunch = (startLunchDate.getTime() - startDate.getTime()) / 60_000;
        Long remainingDuration = getRemainingDuration(durationUntilLunch);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endLunchDate.getTime());
        calendar.add(Calendar.MINUTE, remainingDuration.intValue());
        return calendar.getTime();
    }

    private Date parseToDate(int hours, int minutes) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        try {
            date = format.parse(hours + ":" + minutes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    private Long getRemainingDuration(long durationUntilLunch) {
        return DURATION_OF_DAY - durationUntilLunch;
    }

    private int getStartHour(int startMinutes, State state) {
        if (startMinutes >= 30) {
            return state == START_OF_DAY ? 7 : 11;
        } else {
            return state == START_OF_DAY ? 8 : 12;
        }
    }

    private int getMinutes() {
        Random random = new Random();
        return random.nextInt(60);
    }

}