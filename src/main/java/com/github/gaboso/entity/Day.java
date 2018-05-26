package com.github.gaboso.entity;

public class Day {

    private String dayMonthYear;
    private DurationTime durationTime;

    public Day() {
    }

    public Day(String dayMonthYear) {
        this.dayMonthYear = dayMonthYear;
    }

    public Day(DurationTime durationTime) {
        this.durationTime = durationTime;
    }

    public Day(String dayMonthYear, DurationTime durationTime) {
        this.dayMonthYear = dayMonthYear;
        this.durationTime = durationTime;
    }

    public String getDayMonthYear() {
        return dayMonthYear;
    }

    public void setDayMonthYear(String dayMonthYear) {
        this.dayMonthYear = dayMonthYear;
    }

    public DurationTime getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(DurationTime durationTime) {
        this.durationTime = durationTime;
    }

}