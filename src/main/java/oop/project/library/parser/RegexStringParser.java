package oop.project.library.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * A premade parser for validating strings against a regular expression.
 */
public class RegexStringParser implements Parser<String> {

    private Pattern regexPattern;//The regular expression pattern used to validate the input string.

    /**
     * Creates a new instance of the RegexStringParser with no regular expression pattern.
     */
    public RegexStringParser(){}

    /**
     * Creates a new instance of the RegexStringParser with the specified regular expression pattern.
     *
     *  @param regexString the regular expression pattern to use
     */
    public RegexStringParser(String regexString){
        regexPattern = Pattern.compile(regexString);
    }

    /**
     * Parses the input string and returns the parsed result.
     *
     * If no regular expression pattern was provided, the input string is returned as-is.
     * If the input string matches the provided regular expression, the input string is returned.
     * Otherwise, a RegexParseException is thrown.
     *
     *  @param input the string to be parsed
     *  @return the parsed string
     */
    @Override
    public String parse(String input) {
        //If no regexes were provided then just allow anything
        if(regexPattern == null){
            return input;
        }

        //Otherwise, ensure that the input string matches the regex
        if(regexPattern.matcher(input).matches()){
            return input;
        }

        throw new RegexParseException("The string to be parsed does not match any of the provided regexes.");
    }
}
