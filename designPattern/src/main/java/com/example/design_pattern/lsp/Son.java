package com.example.design_pattern.lsp;

import java.util.Collection;
import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-09 16:55
 **/
public class Son extends Father{

    public <K, V> Collection<V> doSomething(Map<K, V> map){
        System.out.println("son class method doSomething..");
        return map.values();
    }
}
