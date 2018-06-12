package tech.soulcoder.entity;

public interface ActionWithException2<T> {
    void invoke(T t) throws Exception;
}