package com.box.lib.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isListNotEmpty(List list) {
        return !isListEmpty(list);
    }

    public static boolean isListMoreEqualSzie(List list, int size) {
        return !isListEmpty(list) && list.size() >= size;
    }

    public static boolean isListMoreEqualIndex(List list, int index) {
        return !isListEmpty(list) && list.size() > index;
    }

    public static boolean remove(List list, Object object) {
        return isListNotEmpty(list) && list.remove(object);
    }

    public static <T> T objectAtLast(List<T> list) {
        if(isListNotEmpty(list)) {
            int size = list.size();
            return list.get(size - 1);
        } else {
            return null;
        }
    }

    public static <T> T objectAtIndex(List<T> list, int index) {
        return isListNotEmpty(list) && index >= 0 && index < list.size()?list.get(index):null;
    }

    public static <T> boolean append(List<T> list, T t) {
        if(list != null && t != null) {
            list.add(t);
            return true;
        } else {
            return false;
        }
    }

    public static <T> boolean addFirst(List<T> list, T t) {
        if(list != null && t != null) {
            list.add(0, t);
            return true;
        } else {
            return false;
        }
    }

    public static <T> boolean append(List<T> src, List<T> apppendingList) {
        if(src == null) {
            return false;
        } else if(isListEmpty(apppendingList)) {
            return true;
        } else {
            src.addAll(apppendingList);
            return true;
        }
    }

    public static <T> boolean apply(boolean reverse, List<T> list, CommonCallBack<T> call) {
        if(call == null) {
            return false;
        } else if(!isListNotEmpty(list)) {
            return false;
        } else {
            int i;
            if(reverse) {
                for(i = list.size() - 1; i >= 0; --i) {
                    call.apply(list.get(i), i);
                }
            } else {
                for(i = 0; i < list.size(); ++i) {
                    call.apply(list.get(i), i);
                }
            }

            return true;
        }
    }

    public static <T> boolean apply(List<T> list, CommonCallBack<T> call) {
        return apply(false, list, call);
    }

    public static <T> List<T> getFixSizeList(Class<T> bean, int size, CommonCallBack<T> call) {
        List<T> result = new ArrayList();

        for(int i = 0; i < size; ++i) {
            T t;

            try {
                t = bean.newInstance();
                if(call != null) {
                    call.apply(t, i);
                }

                result.add(t);
            } catch (InstantiationException var7) {
                var7.printStackTrace();
            } catch (IllegalAccessException var8) {
                var8.printStackTrace();
            }
        }

        return result;
    }

    public static int size(List src) {
        return src == null?-1:(isListEmpty(src)?0:src.size());
    }

    public static <T> List<T> trimToSize(List<T> list, int size) {
        if(isListNotEmpty(list) && list.size() > size) {
            list = list.subList(0, size);
        }

        return list;
    }

    public interface CommonCallBack<T> {
        void apply(T var1, int var2);
    }

    public static boolean isMapEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isMapNotEmpty(Map map) {
        return !isMapEmpty(map);
    }

    public static boolean isCollectionEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isCollectionNotEmpty(Collection collection) {
        return !isCollectionEmpty(collection);
    }
}
