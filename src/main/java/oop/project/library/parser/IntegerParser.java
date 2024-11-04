package oop.project.library.parser;

public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String input) {
        return Integer.parseInt(input);
    }
}
