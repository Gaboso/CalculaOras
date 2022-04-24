package com.github.gaboso.exception;

public class BadConfigException extends Exception {

    public BadConfigException() {
        super("Invalid number of days: If the days.custom property is TRUE, it is necessary to inform a value for all days in the days property." +
                  "\nExample: If days.quantity is 5, there must be 5 values in the days array.");
    }

}