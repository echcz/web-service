package cn.echcz.webservice.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ISO_DATE);
    }
}
