package tech.soulcoder.entity;

public interface FilterFunc<T> {
    boolean filter(T t);
}
