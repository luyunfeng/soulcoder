package tech.soulcoder.entity;

public class Optional3<T, T2, T3> {
    public T value1;
    public T2 value2;
    public T3 value3;
    public Optional3() {}
    public Optional3(T value1, T2 value2, T3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }
}