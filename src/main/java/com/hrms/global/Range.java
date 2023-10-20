package com.hrms.global;

public class Range<T extends Comparable<T>> {
    private T min;
    private T max;

    public Range(T min, T max) {
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Minimum value must be less than or equal to maximum value.");
        }
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public boolean contains(T value) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    @Override
    public String toString() {
        return "[" + min + " - " + max + "]";
    }

    // You can add more methods and functionality as needed.
}
