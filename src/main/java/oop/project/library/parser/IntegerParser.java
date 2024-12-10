package oop.project.library.parser;

/**
 * A premade parser for integers.
 * Supports restricting the input to a [min, max] inclusive range.
 */
public class IntegerParser extends NumberParser {
    /**
     * Creates a IntegerParser for any integer from [-2147483648, 2147483647] inclusive
     */
    public IntegerParser(){
        super(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Creates a IntegerParser that requires the parsed input to be in the range [min, max] inclusive.
     */
    public IntegerParser(int min, int max) {
        super(min, max);
    }

    /**
     * Attempts to parse the provided string.
     * @param input the string to be parsed
     * @return the parsed int value, or throws a NumberParseException if the input is outside the specified bounds or cannot be parsed as an int.
     */
    @Override
    public Integer parse(String input) {
        try{
            int value = Integer.parseInt(input);
            if(value < (Integer)min || value > (Integer)max){
                throw new NumberParseException("The input is outside of the specified bounds");
            }
            return value;
        }catch (NumberFormatException e){
            throw new NumberParseException("Could not parse the input as an integer");
        }

    }
}
