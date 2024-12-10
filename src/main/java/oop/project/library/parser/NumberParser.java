package oop.project.library.parser;


/**
 * An abstract Parser for Numbers
 * Extensions of this Parser should implement inclusive upper and lower bound restrictions.
 */
public abstract class NumberParser implements Parser<Number> {
    protected Number min;
    protected Number max;

    //Default constructor is removed to force the children to set min and max

    public NumberParser(Number min, Number max) {
        this.min = min;
        this.max = max;
    }

}
