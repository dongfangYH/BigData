package com.example.thread;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @description GroupingBy
 * @date 2020-05-16 09:16
 * @author yuanhang.liu@tcl.com
 **/
public class GroupingBy<T, K> implements Collector<T, Map<K, List<T>>, Map<K, List<T>>> {

    private final static Set<Characteristics> characteristics = new HashSet<>();
    static {
        characteristics.add(Characteristics.IDENTITY_FINISH);
    }

    private final Function<? super T, ? extends K> classifier;

    public GroupingBy(Function<? super T, ? extends K> classifier) {
        this.classifier = classifier;
    }

    // 用于生成结果容器
    public Supplier<Map<K, List<T>>> supplier() {
        return HashMap::new;
    }

    //用于消费元素T，会与容器Map<K, List<T>>进行合并
    public BiConsumer<Map<K, List<T>>, T> accumulator() {
        return (map, element) -> {
            K key = classifier.apply(element);
            List<T> elements = map.computeIfAbsent(key, k -> new ArrayList<>());
            elements.add(element);
        };
    }

    //合并两个容器Map<K, List<T>>
    public BinaryOperator<Map<K, List<T>>> combiner() {
        return (left, right) -> {
            right.forEach((key, value) -> {
                left.merge(key, value, (leftValue, rightValue) -> {
                    leftValue.addAll(rightValue);
                    return leftValue;
                });
            });
            return left;
        };
    }

    // 用与将结果Map<K, List<T>>，转换成结果Map<K, List<T>>
    public Function<Map<K, List<T>>, Map<K, List<T>>> finisher() {
        return map -> map;
    }

    //表示当前collector的特征值
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
}
