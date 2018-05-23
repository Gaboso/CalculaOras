package com.github.gaboso.entity;

public class Day {
    private DurationTime durationTime;
    private String dayMonthYearAsString;

    public Day() {
    }

    public Day(String dayMonthYearAsString) {
        this.dayMonthYearAsString = dayMonthYearAsString;
    }

    public Day(DurationTime durationTime) {
        this.durationTime = durationTime;
    }

    public Day(DurationTime durationTime, String dayMonthYearAsString) {
        this.durationTime = durationTime;
        this.dayMonthYearAsString = dayMonthYearAsString;
    }

    public DurationTime getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(DurationTime durationTime) {
        this.durationTime = durationTime;
    }

    public String getDayMonthYearAsString() {
        return dayMonthYearAsString;
    }

    public void setDayMonthYearAsString(String dayMonthYearAsString) {
        this.dayMonthYearAsString = dayMonthYearAsString;
    }
}
