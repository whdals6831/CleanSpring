package com.jjm.cleanspring.infrastructure.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@WritingConverter
public class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {
    private static final int KST_OFFSET_HOURS = 9;

    @Override
    public Date convert(LocalDateTime source) {
        return Timestamp.valueOf(source.plusHours(KST_OFFSET_HOURS));
    }
}