package oop.project.library.parser;

/**
 * A premade parser for booleans.
 */
public class BooleanParser implements Parser<Boolean> {

    /**
     * Parses the provided string.
     * Everything is false, except the string "true" case-insensitive
     * @param input the string to be parsed
     * @return the parsed boolean value
     */
    @Override
    public Boolean parse(String input) {
        return Boolean.parseBoolean(input);//Surprisingly this never throws an error. Everything is false, except the string "true" case-insensitive
    }
}
