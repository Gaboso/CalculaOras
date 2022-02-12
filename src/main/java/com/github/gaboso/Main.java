package com.github.gaboso;

import com.github.gaboso.config.Props;
import com.github.gaboso.entity.Day;
import com.github.gaboso.utils.DayUtils;

import java.util.List;

public class Main {

    private static final Props PROPS = new Props();

    public static void main(String[] args) throws Exception {
        DayUtils dayUtils = new DayUtils(PROPS);

        List<Day> days = dayUtils.createDaysList();
        dayUtils.validate(days);

        Calculator calculador = new Calculator(PROPS);

        int quantityOfDays = PROPS.getDaysQuantity();
        for (int day = 0; day < quantityOfDays; day++) {
            Day currentDay = days.get(day);
            calculador.updateDay(currentDay);
        }

        GeneratePDF generatePDF = new GeneratePDF(days, PROPS);
        generatePDF.download();
    }

}
