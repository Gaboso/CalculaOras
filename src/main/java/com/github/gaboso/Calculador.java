package com.github.gaboso;

import com.github.gaboso.entity.Day;
import com.github.gaboso.entity.DurationTime;
import com.github.gaboso.entity.Enterprise;
import com.github.gaboso.entity.Worker;
import com.github.gaboso.enumeration.State;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.github.gaboso.Config.CUSTOM_DAYS;
import static com.github.gaboso.Config.DAYS;
import static com.github.gaboso.Config.ENABLE_JUSTIFICATION_ALL_DAYS;
import static com.github.gaboso.Config.ENTERPRISE_CNPJ;
import static com.github.gaboso.Config.ENTERPRISE_NAME;
import static com.github.gaboso.Config.MINUTES_WORKED_BY_DAY;
import static com.github.gaboso.Config.QUANTITY_OF_DAYS;
import static com.github.gaboso.Config.WORKER_NAME;
import static com.github.gaboso.Config.WORKER_PIS;
import static com.github.gaboso.enumeration.State.LUNCH;
import static com.github.gaboso.enumeration.State.START_OF_DAY;

public class Calculador {

    public static void main(String[] args) throws Exception {
        Calculador calculador = new Calculador();

        List<Day> days = createDaysList();

        validateDays(days);

        for (int i = 0; i < QUANTITY_OF_DAYS; i++) {
            DurationTime durationTime = calculador.getDurationTime();
            days.get(i).setDurationTime(durationTime);
        }

        Worker worker = new Worker(WORKER_NAME, WORKER_PIS);
        Enterprise enterprise = new Enterprise(ENTERPRISE_NAME, ENTERPRISE_CNPJ);

        GeneratePDF generatePDF = new GeneratePDF();
        generatePDF.download(days, worker, enterprise, ENABLE_JUSTIFICATION_ALL_DAYS);
    }

    private static void validateDays(List<Day> days) throws Exception {
        if (CUSTOM_DAYS && days.size() != QUANTITY_OF_DAYS) {
            throw new Exception("Quantidade de dias inválida: Caso CUSTOM_DAYS seja TRUE, " +
                    "é necessário informar valor para todos os dias na variável DAYS da classe Config." +
                    "\nExemplo: Se QUANTITY_OF_DAYS for 5, devem existir 5 valores no array DAYS.");
        }
    }

    private static List<Day> createDaysList() {
        List<Day> days = new ArrayList<>();

        if (CUSTOM_DAYS) {
            for (String dayMonthYearAsString : DAYS) {
                days.add(new Day(dayMonthYearAsString));
            }
        } else {
            for (int i = 0; i < QUANTITY_OF_DAYS; i++) {
                days.add(new Day("__/__/____"));
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
            e.printStackTrace();
        }

        return date;
    }

    private Long getRemainingDuration(long durationUntilLunch) {
        return MINUTES_WORKED_BY_DAY - durationUntilLunch;
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