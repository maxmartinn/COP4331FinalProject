package oop.project.library.parser;

public class BooleanParser implements Parser<Boolean> {
    @Override
    public Boolean parse(String input) {
        return Boolean.parseBoolean(input);//Surprisingly this never throws an error. Everything is false, except the string "true" case-insensitive
    }
}
