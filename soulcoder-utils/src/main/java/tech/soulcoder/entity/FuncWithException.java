package tech.soulcoder.entity;

public interface FuncWithException<T> {
    T invoke() throws Exception;
}