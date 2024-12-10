package oop.project.library.parser;

/**
 * A premade parser for doubles.
 * Supports restricting the input to a [min, max] inclusive range.
 */
public class DoubleParser extends NumberParser  {
    /**
     * Creates a DoubleParser for any double from -infinity to infinity
     */
    public DoubleParser(){
        super(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * Creates a DoubleParser that requires the parsed input to be in the range [min, max] inclusive.
     */
    public DoubleParser(double min, double max) {
        super(min, max);
    }

    /**
     * Attempts to parse the provided string.
     * @param input the string to be parsed
     * @return the parsed double value, or throws a NumberParseException if the input is outside the specified bounds or cannot be parsed as a double.
     */
    @Override
    public Double parse(String input) {
        try{
            double value = Double.parseDouble(input);
            if(value < (Double)min || value > (Double)max){
                throw new NumberParseException("The input is outside of the specified bounds");
            }
            return value;
        }catch (NumberFormatException e){
            throw new NumberParseException("Could not parse the input as a double");
        }

    }
}
