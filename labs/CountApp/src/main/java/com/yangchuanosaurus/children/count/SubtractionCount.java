package com.yangchuanosaurus.children.count;

import java.util.ArrayList;
import java.util.List;

public class SubtractionCount {
    private final int maxCount;
    private List<Equation> equationList;

    public SubtractionCount(int maxCount) {
        this.maxCount = maxCount;
        equationList = new ArrayList<>();
        generate();
    }

    private void generate() {
        for (int i = maxCount; i > 0; i--) {
            for (int j = 1; j <= i; j++) {
                equationList.add(new Equation(i, j, Equation.Style.Subtraction));
            }
        }
    }

    public List<Equation> getEquations() {
        return equationList;
    }
}
