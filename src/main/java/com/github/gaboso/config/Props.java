package com.github.gaboso.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {

    private String workerName;
    private String workerSocialSecurityId;

    private String employerName;
    private String employerSocialSecurityId;

    private Boolean enableDaysJustification;
    private Boolean daysCustom;
    private Integer daysQuantity;
    private Integer minutesWorkedPerDay;
    private String outputFileName;

    private String[] days;

    public Props() {
        try (InputStream input = Props.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            Properties properties = new Properties();
            properties.load(input);
            loadPropsValues(properties);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void loadPropsValues(Properties properties) {
        workerName = properties.getProperty("worker.name");
        workerSocialSecurityId = properties.getProperty("worker.socialSecurity.id");

        employerName = properties.getProperty("employer.name");
        employerSocialSecurityId = properties.getProperty("employer.socialSecurity.id");

        enableDaysJustification = Boolean.parseBoolean(properties.getProperty("days.enable.justification"));
        daysQuantity = Integer.parseInt(properties.getProperty("days.quantity"));
        daysCustom = Boolean.parseBoolean(properties.getProperty("days.custom"));
        days = properties.getProperty("days").split(",");
        minutesWorkedPerDay = Integer.parseInt(properties.getProperty("minutes.workedPerDay"));

        outputFileName = properties.getProperty("output.file");
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getWorkerSocialSecurityId() {
        return workerSocialSecurityId;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerSocialSecurityId() {
        return employerSocialSecurityId;
    }

    public Boolean getEnableDaysJustification() {
        return enableDaysJustification;
    }

    public Boolean getDaysCustom() {
        return daysCustom;
    }

    public Integer getDaysQuantity() {
        return daysQuantity;
    }

    public Integer getMinutesWorkedPerDay() {
        return minutesWorkedPerDay;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String[] getDays() {
        return days;
    }
}
