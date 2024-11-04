package oop.project.library.parser;

public class BooleanParser implements Parser<Boolean> {
    @Override
    public Boolean parse(String input) {
        return Boolean.parseBoolean(input);
    }
}
