package com.example.backend.model;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LocalDateFormatter implements Formatter<LocalDateTime> {

    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");


    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text, dateTimeFormatter);
    }

    @Override
    public String print(LocalDateTime object, Locale locale){
        return dateTimeFormatter.format(object);
    }

}
