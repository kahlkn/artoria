package artoria.lang;

import artoria.util.ArrayUtils;
import artoria.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

import static artoria.common.Constants.*;

/**
 * The dictionary is a mutable container model used to hold data with mapped relationships.
 * @author Kahle
 */
public class Dict extends LinkedHashMap<Object, Object> {

    public static Dict of() {

        return new Dict();
    }

    public static Dict of(Object key, Object value) {

        return of().set(key, value);
    }

    public static Dict of(Dict dict) {

        return of().set(dict);
    }

    public static Dict of(Map<?, ?> map) {

        return of().set(map);
    }

    public static Dict of(Object... objects) {

        return of().set(objects);
    }

    public Dict set(Object key, Object value) {
        put(key, value);
        return this;
    }

    public Dict set(Dict dict) {
        putAll(dict);
        return this;
    }

    public Dict set(Map<?, ?> map) {
        putAll(map);
        return this;
    }

    public Dict set(Object... objects) {
        if (ArrayUtils.isEmpty(objects)) { return this; }
        Assert.isTrue(objects.length % TWO == ZERO,
                "The key value array must be a multiple of two. ");
        for (int i = ZERO; i < objects.length; i += TWO) {
            put(objects[i], objects[i + ONE]);
        }
        return this;
    }

    public Dict delete(Object... keys) {
        if (ArrayUtils.isEmpty(keys)) { return this; }
        for (Object key : keys) { remove(key); }
        return this;
    }

}
