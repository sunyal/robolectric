package com.xtremelabs.robolectric.bytecode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings({"UnusedDeclaration"})
public class RobolectricInternals {
    // initialized via magic by AndroidTranslator
    private static ClassHandler classHandler;

    public static ClassHandler getClassHandler() {
        return classHandler;
    }

    private static final ThreadLocal<Vars> ALL_VARS = new ThreadLocal<Vars>() {
        @Override protected Vars initialValue() {
            return new Vars();
        }
    };

    private static class Vars {
        Object callDirectly;
    }

    public static <T> T newInstanceOf(Class<T> clazz) {
        try {
            Constructor<T> defaultConstructor = clazz.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            return defaultConstructor.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Class<T> clazz, Class[] parameterTypes, Object[] params) {
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(params);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T directlyOn(T shadowedObject) {
        Vars vars = ALL_VARS.get();

        if (vars.callDirectly != null) {
            Object expectedInstance = vars.callDirectly;
            vars.callDirectly = null;
            throw new RuntimeException("already expecting a direct call on <" + expectedInstance + "> but here's a new request for <" + shadowedObject + ">");
        }

        vars.callDirectly = shadowedObject;
        return shadowedObject;
    }

    public static boolean shouldCallDirectly(Object directInstance) {
        Vars vars = ALL_VARS.get();
        if (vars.callDirectly != null) {
            if (vars.callDirectly != directInstance) {
                Object expectedInstance = vars.callDirectly;
                vars.callDirectly = null;
                throw new RuntimeException("expected to perform direct call on <" + expectedInstance + "> but got <" + directInstance + ">");
            } else {
                vars.callDirectly = null;
            }
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object methodInvoked(Class clazz, String methodName, Object instance, String[] paramTypes, Object[] params) throws Throwable {
        try {
          return classHandler.methodInvoked(clazz, methodName, instance, paramTypes, params);
        } catch(java.lang.LinkageError e) {
          throw new Exception(e);
        }
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(Object o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(boolean o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(byte o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(char o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(short o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(int o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(long o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(float o) {
        return o;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Object autobox(double o) {
        return o;
    }
}
