package com.example.design_pattern.lsp;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-06-09 16:54
 **/
public class Father {

    public <K, V> Collection<V> doSomething(HashMap<K, V> map){
        System.out.println("father class method doSomething..");
       return map.values();
    }
}
