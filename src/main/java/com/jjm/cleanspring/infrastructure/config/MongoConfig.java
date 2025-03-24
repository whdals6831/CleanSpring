package com.jjm.cleanspring.infrastructure.config;

import com.jjm.cleanspring.infrastructure.converter.DateToLocalDateTimeConverter;
import com.jjm.cleanspring.infrastructure.converter.LocalDateTimeToDateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {
    private final LocalDateTimeToDateConverter localDateTimeConverter;
    private final DateToLocalDateTimeConverter dateConverter;

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext
    ) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);

        // "_class" 타입을 저장하지 않도록 설정
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        // MongoDB KST 변환 컨버터 설정
        converter.setCustomConversions(new MongoCustomConversions(
                List.of(localDateTimeConverter, dateConverter)
        ));

        return converter;
    }
}