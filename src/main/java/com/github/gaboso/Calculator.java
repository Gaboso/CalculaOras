package com.github.gaboso;

import com.github.gaboso.config.Props;
import com.github.gaboso.entity.Day;
import com.github.gaboso.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;

public class Calculator {

    private final DateTimeUtils dateTimeUtils;
    private final Props props;

    public Calculator(Props props) {
        dateTimeUtils = new DateTimeUtils();
        this.props = props;
    }

    public void updateDay(Day day) {
        int startMinutes = dateTimeUtils.generateRandomMinutes();
        int startHour = dateTimeUtils.getStartHour(startMinutes);
        int startLunchMinutes = dateTimeUtils.generateRandomMinutes();
        int startLunchHour = dateTimeUtils.getLunchStartHour(startLunchMinutes);
        int endLunchHour = startLunchHour + 1;

        Date endDate = calculateEndDate(startHour, startMinutes, startLunchHour, startLunchMinutes);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endMinutes = calendar.get(Calendar.MINUTE);

        day.setStartHour(startHour, startMinutes);
        day.setStartLunchHour(startLunchHour, startLunchMinutes);
        day.setEndLunchHour(endLunchHour, startLunchMinutes);
        day.setEndHour(endHour, endMinutes);
    }

    private Date calculateEndDate(int startHour, int startMinutes, int startLunchHour, int startLunchMinutes) {
        Date startDate = dateTimeUtils.toDate(startHour, startMinutes);
        Date startLunchDate = dateTimeUtils.toDate(startLunchHour, startLunchMinutes);
        Date endLunchDate = dateTimeUtils.toDate(startLunchHour + 1, startLunchMinutes);

        long durationUntilLunch = (startLunchDate.getTime() - startDate.getTime()) / 60_000;
        long remainingDuration = props.getMinutesWorkedPerDay() - durationUntilLunch;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endLunchDate.getTime());
        calendar.add(Calendar.MINUTE, (int) remainingDuration);
        return calendar.getTime();
    }

}