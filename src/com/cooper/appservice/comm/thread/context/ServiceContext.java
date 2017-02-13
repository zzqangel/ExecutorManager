package com.cooper.appservice.comm.thread.context;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-20
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class ServiceContext {
    private ConcurrentHashMap<String, ValueObject> dataMap = new ConcurrentHashMap<String, ValueObject>();
    public ServiceContext() {
    }

    /**
     * 将参数设置到context中
     * @param key
     * @param data
     */
    public boolean setDataForKey(String key, Object data) {
        return setDataForKey(key, data, true);
    }
    /**
     * 将参数设置到context中
     * @param key
     * @param data
     * @param replaceable 当前key和对应的data是否可以被替换
     */
    public boolean setDataForKey(String key, Object data, boolean replaceable) {
        if(StringUtils.isBlank(key)) return false;
        ValueObject valueObject = this.dataMap.get(key);
        if(valueObject != null && !valueObject.removeAble) return false;
        this.dataMap.put(key, new ValueObject(data, replaceable));
        return true;
    }

    public Object getDataForKey(String key) {
        return getDataForKey(key, false);
    }

    public Object removeDataForKey(String key) {
        return getDataForKey(key, true);
    }

    private Object getDataForKey(String key, boolean removable) {
        if(StringUtils.isBlank(key)) return null;
        ValueObject valueObject = removable? this.dataMap.remove(key) : this.dataMap.get(key);
        if(valueObject == null) return null;
        return valueObject.value;
    }
}
