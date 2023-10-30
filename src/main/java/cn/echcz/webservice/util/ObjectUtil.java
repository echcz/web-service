package cn.echcz.webservice.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Object 工具类
 */
public class ObjectUtil {
    private ObjectUtil() {
        // Util class
    }

    /**
     * 获取第一个不为 null 的值，如果都为空则返回 null
     */
    public static <T> T firstNonNull(T... objects) {
        for (T object : objects) {
            if (object != null) {
                return object;
            }
        }
        return null;
    }

    /**
     * 如果值不为空则返回此值，否则返回 supplier 生成的值
     */
    public static <T> T notNullOr(T object, Supplier<? extends T> supplier) {
        if (object == null) {
            return supplier.get();
        }
        return object;
    }

    /**
     * 如果值不为空则返回 function 计算出的值，否则返回 null
     */
    public static <T, R> R notNullAnd(T object, Function<T, R> function) {
        if (object == null) {
            return null;
        }
        return function.apply(object);
    }
}
