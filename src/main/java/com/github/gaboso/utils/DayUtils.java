package com.github.gaboso.utils;

import com.github.gaboso.config.Props;
import com.github.gaboso.entity.Day;
import com.github.gaboso.exception.BadConfigException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayUtils {

    private final Props props;

    public DayUtils(Props props) {
        this.props = props;
    }

    public List<Day> createDaysList() {
        List<Day> days = new ArrayList<>();
        boolean customDays = props.getDaysCustom();

        if (customDays) {
            days = Arrays.stream(props.getDays())
                         .map(Day::new)
                         .collect(Collectors.toList());
        } else {
            int quantityOfDays = props.getDaysQuantity();
            for (int day = 0; day < quantityOfDays; day++) {
                days.add(new Day("___/___/_____"));
            }
        }

        return days;
    }

    public void validate(List<Day> days) throws BadConfigException {
        boolean customDays = props.getDaysCustom();
        int quantityOfDays = props.getDaysQuantity();

        if (customDays && days.size() != quantityOfDays) {
            throw new BadConfigException();
        }
    }

}
