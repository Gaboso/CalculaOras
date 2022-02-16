package com.github.gaboso.entity;

public class Day {

    private String dayMonthYear;

    private String startHour;
    private String startLunchHour;
    private String endLunchHour;
    private String endHour;

    public Day() {
    }

    public Day(String dayMonthYear) {
        this.dayMonthYear = dayMonthYear;
    }

    public String getDayMonthYear() {
        return dayMonthYear;
    }

    public void setDayMonthYear(String dayMonthYear) {
        this.dayMonthYear = dayMonthYear;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour, int startMinutes) {
        this.startHour = formatTime(startHour, startMinutes);
    }

    public String getStartLunchHour() {
        return startLunchHour;
    }

    public void setStartLunchHour(int startLunchHour, int startLunchMinutes) {
        this.startLunchHour = formatTime(startLunchHour, startLunchMinutes);
    }

    public String getEndLunchHour() {
        return endLunchHour;
    }

    public void setEndLunchHour(int endLunchHour, int endLunchMinutes) {
        this.endLunchHour = formatTime(endLunchHour, endLunchMinutes);
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour, int endMinutes) {
        this.endHour = formatTime(endHour, endMinutes);
    }

    private String formatTime(int hour, int minute) {
        return formatNumber(hour) + ":" + formatNumber(minute);
    }

    private String formatNumber(int number) {
        return String.format("%02d", number);
    }

}