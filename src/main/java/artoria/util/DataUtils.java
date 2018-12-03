package artoria.util;

import artoria.beans.BeanUtils;
import artoria.converter.TypeConvertUtils;
import artoria.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data handle tools.
 * @author Kahle
 */
public class DataUtils {

    /**
     * A java bean list to divide into groups.
     * @param list A java bean list
     * @param groupSize Grouping size
     * @param <T> Java bean type
     * @return A list bean list
     */
    public static <T> List<List<T>> listToListList(List<T> list, int groupSize) {
        List<List<T>> result = new ArrayList<List<T>>();
        if (CollectionUtils.isEmpty(list)) { return result; }
        Assert.state(groupSize != 0, "Parameter \"groupSize\" must not equal 0. ");
        int listSize = list.size();
        int count = listSize / groupSize;
        count = listSize % groupSize == 0 ? count : ++count;
        List<T> tmp;
        for (int i = 0; i < count; i++) {
            int fromIndex = i * groupSize;
            int toIndex = (i + 1) * groupSize;
            toIndex = toIndex > listSize ? listSize : toIndex;
            tmp = new ArrayList<T>(list.subList(fromIndex, toIndex));
            result.add(tmp);
        }
        return result;
    }

    /**
     * A java bean list to convert a java bean property list.
     * @param list A java bean list
     * @param propertyName Java bean property name
     * @param propertyClass Java bean property type
     * @param <R> Java bean property type
     * @param <P> Java bean type
     * @return Java bean property list
     */
    @SuppressWarnings("unchecked")
    public static <R, P> List<R> listToListProperty(List<P> list, String propertyName, Class<R> propertyClass) {
        try {
            Assert.notBlank(propertyName, "Parameter \"propertyName\" must not blank. ");
            Assert.notNull(propertyClass, "Parameter \"propertyClass\" must not null. ");
            // Handle parameters
            List<R> result = new ArrayList<R>();
            if (CollectionUtils.isEmpty(list)) { return result; }
            List<Map<String, Object>> mapList = BeanUtils.beanToMapInList(list);
            for (Map<String, Object> map : mapList) {
                if (map == null) { continue; }
                Object val = map.get(propertyName);
                val = TypeConvertUtils.convert(val, propertyClass);
                result.add((R) val);
            }
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * A java bean list to convert a map who key is bean properties and value is bean.
     * This means if bean property is repeated and value will override.
     * @param list A java bean list
     * @param propertyNames Method property names
     * @param <T> Java bean type
     * @return A map key is bean property and value is bean
     */
    public static <T> Map<String, T> listToMapBean(List<T> list, String... propertyNames) {
        try {
            Assert.notEmpty(propertyNames, "Parameter \"propertyNames\" must not empty. ");
            // Handle parameters
            Map<String, T> result = new HashMap<String, T>(list.size());
            if (CollectionUtils.isEmpty(list)) { return result; }
            List<Map<String, Object>> mapList = BeanUtils.beanToMapInList(list);
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0, len = list.size(); i < len; i++) {
                Map<String, Object> map = mapList.get(i);
                T bean = list.get(i);
                if (map == null || bean == null) { continue; }
                keyBuilder.setLength(0);
                for (String propertyName : propertyNames) {
                    Object val = map.get(propertyName);
                    keyBuilder.append(val);
                }
                result.put(keyBuilder.toString(), bean);
            }
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * A java bean list to convert a map who key is bean properties and value is bean list.
     * This means bean property in bean list have repeated.
     * @param list A java bean list
     * @param propertyNames Method property names
     * @param <T> Java bean type
     * @return A map key is bean property and value is bean list
     */
    public static <T> Map<String, List<T>> listToMapList(List<T> list, String... propertyNames) {
        try {
            Assert.notEmpty(propertyNames, "Parameter \"propertyNames\" must not empty. ");
            // Handle parameters
            Map<String, List<T>> result = new HashMap<String, List<T>>(list.size());
            if (CollectionUtils.isEmpty(list)) { return result; }
            List<Map<String, Object>> mapList = BeanUtils.beanToMapInList(list);
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0, len = list.size(); i < len; i++) {
                Map<String, Object> map = mapList.get(i);
                T bean = list.get(i);
                if (map == null || bean == null) { continue; }
                keyBuilder.setLength(0);
                for (String propertyName : propertyNames) {
                    Object val = map.get(propertyName);
                    keyBuilder.append(val);
                }
                String key = keyBuilder.toString();
                List<T> val = result.get(key);
                if (val == null) {
                    val = new ArrayList<T>();
                    result.put(key, val);
                }
                val.add(bean);
            }
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    /**
     * A java bean list to convert a map who key is bean properties and value is bean property.
     * @param list A java bean list
     * @param valueProperty Map's value corresponding method property names
     * @param keyProperties Map's key corresponding method properties names
     * @param <R> Map's value type
     * @param <P> Java bean type
     * @return A map key is bean properties and value is bean property
     */
    @SuppressWarnings("unchecked")
    public static <R, P> Map<String, R> listToMapProperty(List<P> list, String valueProperty, String... keyProperties) {
        try {
            Assert.notBlank(valueProperty, "Parameter \"valueProperty\" must not blank. ");
            Assert.notEmpty(keyProperties, "Parameter \"keyProperties\" must not empty. ");
            // Handle parameters
            Map<String, R> result = new HashMap<String, R>(list.size());
            if (CollectionUtils.isEmpty(list)) { return result; }
            List<Map<String, Object>> mapList = BeanUtils.beanToMapInList(list);
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0, len = list.size(); i < len; i++) {
                Map<String, Object> map = mapList.get(i);
                P bean = list.get(i);
                if (map == null || bean == null) { continue; }
                keyBuilder.setLength(0);
                for (String keyProperty : keyProperties) {
                    Object val = map.get(keyProperty);
                    keyBuilder.append(val);
                }
                R val = (R) map.get(valueProperty);
                result.put(keyBuilder.toString(), val);
            }
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

}
