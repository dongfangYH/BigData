package com.example.algo.structcure.list;

public interface List<V> {

    /**
     *  添加
     * @param v
     */
    void add(V v);

    /**
     * 删除
     * @param index
     */
    void delete(Integer index);

    /**
     * 删除
     * @param v
     */
    boolean remove(V v);

    /**
     * 获取
     * @param index
     * @return
     */
    V get(Integer index);
}
