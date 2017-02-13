package com.cooper.appservice.comm.thread.context;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-12-21
 * Time: ионГ10:47
 * To change this template use File | Settings | File Templates.
 */
public class ValueObject {
    boolean removeAble;
    Object value;

    ValueObject(Object value, boolean removeAble) {
        this.removeAble = removeAble;
        this.value = value;
    }

}
