package cn.echcz.webservice.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalTime.parse(source, DateTimeFormatter.ISO_TIME);
    }
}
