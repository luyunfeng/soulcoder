package tech.soulcoder.entity;

public interface FuncWithException2<R, T> {
    R invoke(T t) throws Exception;
}
