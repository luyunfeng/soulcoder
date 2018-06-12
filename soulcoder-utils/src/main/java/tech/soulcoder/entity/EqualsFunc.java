package tech.soulcoder.entity;

public interface EqualsFunc<T> {
    boolean invoke(T t1, T t2);
}
