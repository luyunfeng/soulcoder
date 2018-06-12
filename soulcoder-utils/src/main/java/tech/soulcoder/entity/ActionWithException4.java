package tech.soulcoder.entity;

public interface ActionWithException4<T, T2, T3> {
    void invoke(T t, T2 t2, T3 t3) throws Exception;
}