package oop.project.library.parser;

public class DifficultyParser implements Parser<String> {
    @Override
    public String parse(String input) {
        if(input.equals("easy") || input.equals("normal") || input.equals("hard") || input.equals("peaceful")){
            return input;
        }

        throw new RuntimeException("The only difficulties that exist are easy, normal, hard, and peaceful.");
    }
}
