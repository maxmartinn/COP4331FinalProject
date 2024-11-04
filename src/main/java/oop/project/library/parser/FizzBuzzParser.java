package oop.project.library.parser;

public class FizzBuzzParser implements Parser<Integer> {

    @Override
    public Integer parse(String input) {
        int value = Integer.parseInt(input);
        if(value < 1 || value > 100){
            throw new RuntimeException("You cannot fizzbuzz below 1 and above 100.");
        }

        return value;
    }
}
