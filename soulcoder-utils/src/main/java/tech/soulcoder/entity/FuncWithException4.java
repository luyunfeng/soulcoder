package tech.soulcoder.entity;

public interface FuncWithException4<R, T1, T2, T3> {
    R invoke(T1 t1, T2 t2, T3 t3) throws Exception;
}