package oop.project.library.parser;

public class DoubleParser extends NumberParser  {
    public DoubleParser(){
        super(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }
    public DoubleParser(double min, double max) {
        super(min, max);
    }

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
