package com.github.gaboso;

import com.github.gaboso.config.Props;
import com.github.gaboso.entity.Day;
import com.github.gaboso.entity.DurationTime;
import com.github.gaboso.enumeration.State;
import com.github.gaboso.exception.BadConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.github.gaboso.enumeration.State.LUNCH;
import static com.github.gaboso.enumeration.State.START_OF_DAY;

public class Calculador {

    private static final Logger LOGGER = LoggerFactory.getLogger(Calculador.class);
    private static Props props = new Props();

    public static void main(String[] args) throws Exception {
        Calculador calculador = new Calculador();

        List<Day> days = createDaysList();
        validateDays(days);

        int quantityOfDays = props.getDaysQuantity();
        for (int i = 0; i < quantityOfDays; i++) {
            DurationTime durationTime = calculador.getDurationTime();
            days.get(i).setDurationTime(durationTime);
        }

        GeneratePDF generatePDF = new GeneratePDF(days, props);
        generatePDF.download();
    }

    private static void validateDays(List<Day> days) throws BadConfigException {
        boolean customDays = props.getDaysCustom();
        int quantityOfDays = props.getDaysQuantity();

        if (customDays && days.size() != quantityOfDays) {
            throw new BadConfigException();
        }
    }

    private static List<Day> createDaysList() {
        List<Day> days = new ArrayList<>();
        boolean customDays = props.getDaysCustom();

        if (customDays) {
            days = Arrays.stream(props.getDays())
                         .map(Day::new)
                         .collect(Collectors.toList());
        } else {
            int quantityOfDays = props.getDaysQuantity();
            for (int i = 0; i < quantityOfDays; i++) {
                days.add(new Day("___/___/_____"));
            }
        }

        return days;
    }

    private DurationTime getDurationTime() {
        int startMinutes = generateMinutes();
        int startHour = getStartHour(startMinutes, START_OF_DAY);
        int startLunchMinutes = generateMinutes();
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

        DurationTime durationTime = new DurationTime();
        durationTime.setStartHour(startHour, startMinutes);
        durationTime.setStartLunchHour(startLunchHour, startLunchMinutes);
        durationTime.setEndLunchHour(endLunchHour, startLunchMinutes);
        durationTime.setEndHour(endHour, endMinutes);

        return durationTime;
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
            LOGGER.error(e.getMessage(), e);
        }

        return date;
    }

    private Long getRemainingDuration(long durationUntilLunch) {
        int minutesWorkedByDay = props.getMinutesWorkedPerDay();
        return minutesWorkedByDay - durationUntilLunch;
    }

    private int getStartHour(int startMinutes, State state) {
        if (startMinutes >= 30) {
            return state == START_OF_DAY ? 7 : 11;
        } else {
            return state == START_OF_DAY ? 8 : 12;
        }
    }

    private int generateMinutes() {
        Random random = new Random();
        return random.nextInt(60);
    }

}