package com.example.backend.model;
/**
import lombok.NonNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class LocalDateFormatter implements Formatter<LocalDateTime> {

    private static final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");


    @Override
    @NonNull
    public LocalDateTime parse(@NonNull String text,@NonNull Locale locale) throws ParseException {
        return LocalDateTime.parse(text, dateTimeFormatter);
    }

    @Override
    @NonNull
    public String print(@NonNull LocalDateTime object,@NonNull Locale locale){
        return dateTimeFormatter.format(object);
    }

}
**/