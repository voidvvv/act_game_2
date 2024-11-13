package com.voidvvv.game.utils;

public class ReflectUtil {
    public static <T> T cast(Object t, Class<T> clazz) {
        try {
            return (T) t;
        } catch (Throwable e) {
            return null;
        }
    }
}
