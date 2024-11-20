package oop.project.library.parser;

public class FloatParser extends NumberParser  {
    public FloatParser(){
        super(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }
    public FloatParser(float min, float max) {
        super(min, max);
    }

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
