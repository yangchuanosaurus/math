package com.yangchuanosaurus.children.count;

public class CountFactory {
    /**
     * Create Addition Count Exercises
     * @param maxCount max count
     * */
    public static AdditionCount createAdditionCount(int maxCount) {
        return new AdditionCount(maxCount);
    }

    public static SubtractionCount createSubtractionCount(int maxCount) {
        return new SubtractionCount(maxCount);
    }
}
