package oop.project.library.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * A premade parser that validates the input string against a set of allowed values.
 */
public class StringParser implements Parser<String> {
    private Set<String> allowedValues;

    /**
     * Creates a new instance of the StringParser with no allowed values.
     */
    public StringParser(){}

    /**
     * Creates a new instance of the StringParser with the specified allowed values.
     *
     * @param allowedStrings the set of allowed string values
     */
    public StringParser(String... allowedStrings){
        allowedValues = Arrays.stream(allowedStrings).collect(Collectors.toSet());
    }

    /**
     * Parses the input string and returns the parsed result.
     *
     * If no allowed values were provided, the input string is returned as-is.
     * If the input string matches one of the allowed values, the input string is returned.
     * Otherwise, a StringParseException is thrown.
     *
     * @param input the string to be parsed
     * @return the parsed string
     */
    @Override
    public String parse(String input) throws StringParseException {
        //If no target strings were provided then just allow anything
        if(allowedValues == null){
            return input;
        }

        //Otherwise, ensure that the input matches one of the allowed strings
        if(allowedValues.contains(input)){
            return input;
        }

        throw new StringParseException("The string to be parsed is not in the set of allowed string values.");
    }
}
