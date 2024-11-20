package oop.project.library.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexStringParser implements Parser<String> {
    private Set<Pattern> allowedValues;

    public RegexStringParser(){}
    public RegexStringParser(String... allowedStrings){
        allowedValues = Arrays.stream(allowedStrings).map(Pattern::compile).collect(Collectors.toSet());
    }

    @Override
    public String parse(String input) {
        //If no regexes were provided then just allow anything
        if(allowedValues == null){
            return input;
        }

        //Otherwise, ensure that the input string matches to at least one of the regexes
        if(allowedValues.stream().anyMatch(pattern->{return pattern.matcher(input).matches();})){
            return input;
        }

        throw new RegexParseException("The string to be parsed does not match any of the provided regexes.");
    }
}
