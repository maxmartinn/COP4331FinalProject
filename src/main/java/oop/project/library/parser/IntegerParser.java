package oop.project.library.parser;

public class IntegerParser extends NumberParser {
    public IntegerParser(){
        super(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public IntegerParser(int min, int max) {
        super(min, max);
    }

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
