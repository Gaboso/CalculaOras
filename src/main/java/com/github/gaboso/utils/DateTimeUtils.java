package com.github.gaboso.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DateTimeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtils.class);

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private final Random random = new Random();

    public Date toDate(int hours, int minutes) {
        try {
            return timeFormat.parse(hours + ":" + minutes);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return new Date();
    }

    public int generateRandomMinutes() {
        return random.nextInt(60);
    }

    public int getStartHour(int startMinutes) {
        return startMinutes >= 30 ? 7 : 8;
    }

    public int getLunchStartHour(int startMinutes) {
        return startMinutes >= 30 ? 11 : 12;
    }

}
