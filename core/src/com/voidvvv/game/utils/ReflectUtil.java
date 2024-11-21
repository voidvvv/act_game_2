package com.voidvvv.game.utils;

public class ReflectUtil {
    public static <T> T cast(Object t, Class<T> clazz) {
        try {
            if (t!= null && clazz.isAssignableFrom(t.getClass())) {
                return (T) t;
            }
        } catch (Throwable e) {
            System.out.println("cast error for : " + t.getClass() + " : " + e.getMessage());
        }
        return null;
    }
}
