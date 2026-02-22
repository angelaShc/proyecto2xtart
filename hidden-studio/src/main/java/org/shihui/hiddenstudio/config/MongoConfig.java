package org.shihui.hiddenstudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE,
                Jsr310Converters.DateToLocalDateTimeConverter.INSTANCE
        ));
    }

}
