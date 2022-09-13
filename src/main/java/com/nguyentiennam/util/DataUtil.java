package com.nguyentiennam.util;

import java.util.Collection;

public class DataUtil {
    public static boolean isEmptyCollection(Collection<?> collection) {
        return isEmptyObject(collection) || collection.isEmpty();
    }

    public static boolean isEmptyObject(Object obj) {
        return obj == null;
    }

}
