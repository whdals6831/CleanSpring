package com.jjm.cleanspring.infrastructure.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@ReadingConverter
public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {
    private static final int KST_OFFSET_HOURS = 9;

    @Override
    public LocalDateTime convert(Date source) {
        LocalDateTime localDateTime = source.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.minusHours(KST_OFFSET_HOURS);
    }
}
