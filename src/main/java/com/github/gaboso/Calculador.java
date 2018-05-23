package com.github.gaboso;

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

import static com.github.gaboso.enumeration.State.LUNCH;
import static com.github.gaboso.enumeration.State.START_OF_DAY;

public class Calculador {

    private static final int DURATION_OF_DAY = 528;

    public static void main(String[] args) {
        Calculador calculador = new Calculador();

        int quantityOfDays = 20;

        List<DurationTime> days = new ArrayList<>();

        for (int i = 0; i < quantityOfDays; i++) {
            DurationTime durationTime = calculador.getDurationTime();
            days.add(durationTime);
        }

        Worker worker = new Worker();
        worker.setName("John Doe");
        worker.setPis("XXXXXXXXXXX");

        Enterprise enterprise = new Enterprise();
        enterprise.setName("Fun Toys Inc.");
        enterprise.setCnpj("60701020304050");

        GeneratePDF generatePDF = new GeneratePDF();
        generatePDF.download(days, worker, enterprise, true);
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
        return DURATION_OF_DAY - durationUntilLunch;
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