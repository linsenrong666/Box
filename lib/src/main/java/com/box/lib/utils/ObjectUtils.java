package com.box.lib.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 工具类
 * @author Linsr
 */
public class ObjectUtils {

    /**
     * 检查对象是否为空
     * @return true 为空
     *         false 不为空
     */
    public static boolean isNull(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        }
        return false;
    }

    /**
     * 不全为空，即有一个不是空就可以
     */
    public static boolean isNotAllNull(Object... objects) {
        for (Object o : objects) {
            if (isNotNull(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 全都为空
     */
    public static boolean isAllNull(Object... objects) {
        for (Object o : objects) {
            if (isNotNull(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全都为不空,有一个为空就不行
     */
    public static boolean isAllNotNull(Object... objects) {
        for (Object o : objects) {
            if (isNull(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查对象是否不为空
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }
}