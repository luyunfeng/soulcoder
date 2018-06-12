package tech.soulcoder.entity;

public interface FuncWithException3<R, T1, T2> {
    R invoke(T1 t1, T2 t2) throws Exception;
}