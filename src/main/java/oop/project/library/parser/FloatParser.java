package oop.project.library.parser;

/**
 * A premade parser for floats.
 * Supports restricting the input to a [min, max] inclusive range.
 */
public class FloatParser extends NumberParser  {
    /**
     * Creates a FloatParser for any float from -infinity to infinity
     */
    public FloatParser(){
        super(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /**
     * Creates a FloatParser that requires the parsed input to be in the range [min, max] inclusive.
     */
    public FloatParser(float min, float max) {
        super(min, max);
    }

    /**
     * Attempts to parse the provided string.
     * @param input the string to be parsed
     * @return the parsed float value, or throws a NumberParseException if the input is outside the specified bounds or cannot be parsed as a float.
     */
    @Override
    public Float parse(String input) {
        try{
            float value = Float.parseFloat(input);
            if(value < (Float)min || value > (Float)max){
                throw new NumberParseException("The input is outside of the specified bounds");
            }
            return value;
        }catch (NumberFormatException e) {
            throw new NumberParseException("Could not parse the input as a float");
        }
    }
}
