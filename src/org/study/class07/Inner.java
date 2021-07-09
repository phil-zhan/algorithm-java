package org.study.class07;

/**
 * 内部包装类，避免在反向索引表（HashMap）中，基础类型被覆盖
 *
 * @author phil
 * @date 2021/6/10 10:46
 */
public class Inner<T> {
    public T value;

    public Inner(T v) {
        value = v;
    }
}
