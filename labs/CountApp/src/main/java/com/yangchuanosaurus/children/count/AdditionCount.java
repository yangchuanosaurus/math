package com.yangchuanosaurus.children.count;

import java.util.ArrayList;
import java.util.List;

public class AdditionCount {
    private final int maxCount;
    private List<Equation> equationList;

    public AdditionCount(int maxCount) {
        this.maxCount = maxCount;
        equationList = new ArrayList<>();
        generate();
    }

    private void generate() {
        for (int i = 1; i < maxCount; i++) {
            for (int j = maxCount - i; j > 0; j--) {
                equationList.add(new Equation(i, j, Equation.Style.Addition));
            }
        }
    }

    public List<Equation> getEquations() {
        return equationList;
    }
}
