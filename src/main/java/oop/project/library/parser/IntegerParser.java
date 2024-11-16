package oop.project.library.parser;

public class IntegerParser implements Parser<Integer> {
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    public IntegerParser(){}
    public IntegerParser(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer parse(String input) {
        int value = Integer.parseInt(input);
        if(value < min || value > max){
            throw new RuntimeException("Invalid input out of bounds");
        }
        return value;
    }
}
