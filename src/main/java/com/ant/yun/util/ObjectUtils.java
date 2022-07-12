package com.ant.yun.util;

import com.ant.yun.lang.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 12:39
 */
public abstract class ObjectUtils {

    public static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }
    public static boolean nullSafeEquals(@Nullable Object o1, @Nullable Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 != null && o2 != null) {
            if (o1.equals(o2)) {
                return true;
            } else {
                return o1.getClass().isArray() && o2.getClass().isArray() ? arrayEquals(o1, o2) : false;
            }
        } else {
            return false;
        }
    }
    public static boolean isEmpty(@Nullable Object[] array) {
        return array == null || array.length == 0;
    }

    public static String nullSafeToString(@Nullable Object obj) {
        if (obj == null) {
            return "null";
        } else if (obj instanceof String) {
            return (String)obj;
        } else if (obj instanceof Object[]) {
            return nullSafeToString((Object[])((Object[])obj));
        } else if (obj instanceof boolean[]) {
            return nullSafeToString((boolean[])((boolean[])obj));
        } else if (obj instanceof byte[]) {
            return nullSafeToString((byte[])((byte[])obj));
        } else if (obj instanceof char[]) {
            return nullSafeToString((char[])((char[])obj));
        } else if (obj instanceof double[]) {
            return nullSafeToString((double[])((double[])obj));
        } else if (obj instanceof float[]) {
            return nullSafeToString((float[])((float[])obj));
        } else if (obj instanceof int[]) {
            return nullSafeToString((int[])((int[])obj));
        } else if (obj instanceof long[]) {
            return nullSafeToString((long[])((long[])obj));
        } else if (obj instanceof short[]) {
            return nullSafeToString((short[])((short[])obj));
        } else {
            String str = obj.toString();
            return str != null ? str : "";
        }
    }

    private static boolean arrayEquals(Object o1, Object o2) {
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[])((Object[])o1), (Object[])((Object[])o2));
        } else if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[])((boolean[])o1), (boolean[])((boolean[])o2));
        } else if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[])((byte[])o1), (byte[])((byte[])o2));
        } else if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[])((char[])o1), (char[])((char[])o2));
        } else if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[])((double[])o1), (double[])((double[])o2));
        } else if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[])((float[])o1), (float[])((float[])o2));
        } else if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[])((int[])o1), (int[])((int[])o2));
        } else if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[])((long[])o1), (long[])((long[])o2));
        } else {
            return o1 instanceof short[] && o2 instanceof short[] ? Arrays.equals((short[])((short[])o1), (short[])((short[])o2)) : false;
        }
    }

    public static int nullSafeHashCode(@Nullable Object obj) {
        if (obj == null) {
            return 0;
        } else {
            if (obj.getClass().isArray()) {
                if (obj instanceof Object[]) {
                    return nullSafeHashCode((Object[])((Object[])obj));
                }

                if (obj instanceof boolean[]) {
                    return nullSafeHashCode((boolean[])((boolean[])obj));
                }

                if (obj instanceof byte[]) {
                    return nullSafeHashCode((byte[])((byte[])obj));
                }

                if (obj instanceof char[]) {
                    return nullSafeHashCode((char[])((char[])obj));
                }

                if (obj instanceof double[]) {
                    return nullSafeHashCode((double[])((double[])obj));
                }

                if (obj instanceof float[]) {
                    return nullSafeHashCode((float[])((float[])obj));
                }

                if (obj instanceof int[]) {
                    return nullSafeHashCode((int[])((int[])obj));
                }

                if (obj instanceof long[]) {
                    return nullSafeHashCode((long[])((long[])obj));
                }

                if (obj instanceof short[]) {
                    return nullSafeHashCode((short[])((short[])obj));
                }
            }

            return obj.hashCode();
        }
    }
    public static boolean containsElement(@Nullable Object[] array, Object element) {
        if (array == null) {
            return false;
        } else {
            Object[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object arrayEle = var2[var4];
                if (nullSafeEquals(arrayEle, element)) {
                    return true;
                }
            }

            return false;
        }
    }
    public static Object[] toObjectArray(@Nullable Object source) {
        if (source instanceof Object[]) {
            return (Object[])((Object[])source);
        } else if (source == null) {
            return new Object[0];
        } else if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        } else {
            int length = Array.getLength(source);
            if (length == 0) {
                return new Object[0];
            } else {
                Class<?> wrapperType = Array.get(source, 0).getClass();
                Object[] newArray = (Object[])((Object[])Array.newInstance(wrapperType, length));

                for(int i = 0; i < length; ++i) {
                    newArray[i] = Array.get(source, i);
                }

                return newArray;
            }
        }
    }
}
