package com.yangchuanosaurus.children.count;

public class Equation {
    private int number;
    private int anotherNumber;
    private Style style;

    public enum Style {
        Addition, Subtraction
    }

    public Equation(int number, int anotherNumber, Style style) {
        this.number = number;
        this.anotherNumber = anotherNumber;
        this.style = style;
    }

    public int getNumber() {
        return number;
    }

    public int getAnotherNumber() {
        return anotherNumber;
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public String toString() {
        if (Style.Addition == style) {
            return number + " + " + anotherNumber + " = " + " ? ";
        } else if (Style.Subtraction == style) {
            return number + " - " + anotherNumber + " = " + " ? ";
        }
        return "Equation{" +
                "number=" + number +
                ", anotherNumber=" + anotherNumber +
                ", style=" + style.name() +
                '}';
    }
}
