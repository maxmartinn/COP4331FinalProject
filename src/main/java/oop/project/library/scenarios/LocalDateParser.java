package oop.project.library.scenarios;

import oop.project.library.parser.ParseException;
import oop.project.library.parser.Parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//Since the weekday command is supposed to test how the library handles types without build in parsers, I have put the LocalDateParser in scenarios.
//As though it was a parser written by the user
//Alternatively, I have misinterpreted what you meant by that
public class LocalDateParser implements Parser<LocalDate> {
    @Override
    public LocalDate parse(String input) {
        try{
            return LocalDate.parse(input);
        }catch(DateTimeParseException e){
            throw new ParseException("The provided string could not be parsed as a date");
        }
    }
}
