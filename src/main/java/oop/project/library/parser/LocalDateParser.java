package oop.project.library.parser;

import java.time.LocalDate;

public class LocalDateParser implements Parser<LocalDate> {
    @Override
    public LocalDate parse(String input) {
        return LocalDate.parse(input);
    }
}
