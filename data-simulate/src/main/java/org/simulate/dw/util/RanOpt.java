package org.simulate.dw.util;

/**
 *
 * @desc
 *
 */
public class RanOpt<T>{
    T value;

    public RanOpt(T value, int weight) {
        this.value = value;
        this.weight = weight;
    }
    int weight;

    public T getValue() { return (T)this.value; }



    public int getWeight() { return this.weight; }
}