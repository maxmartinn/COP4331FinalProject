package oop.project.library.command;

import oop.project.library.parser.Parser;

class CommandArgument {
    private Parser<?> parser;//This must not be null

    CommandArgument(Parser<?> parser){
        this.parser = parser;
    }

    Object parse(String input){
        return parser.parse(input);
    }

}
