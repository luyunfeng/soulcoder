package tech.soulcoder.entity;

public interface ActionWithException3<T, T2> {
    void invoke(T t, T2 t2) throws Exception;
}