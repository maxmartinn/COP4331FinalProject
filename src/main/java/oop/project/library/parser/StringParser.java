package oop.project.library.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StringParser implements Parser<String> {
    private Set<String> allowedValues;

    public StringParser(){}
    public StringParser(String... allowedStrings){
        allowedValues = Arrays.stream(allowedStrings).collect(Collectors.toSet());
    }

    @Override
    public String parse(String input) {
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
