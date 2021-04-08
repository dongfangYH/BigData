package com.example.gof.behavior.strategy;

import java.util.HashMap;
import java.util.Map;

public class StrategyFactory {

    private StrategyFactory(){
        // 打不赢，跳过
        cache.put("skip", new SkipStrategy());
        // 打的赢，直接打吧
        cache.put("hit", new HitStrategy());
    }

    private static final Map<String, Strategy> cache = new HashMap<>();

    /**
     * 从缓存获取策略
     * @param type
     * @return
     */
    public static Strategy getStrategy(String type){
        return cache.get(type);
    }

    /**
     * 每次产生的是新的策略，适合有状态变化的场景
     * @param type
     * @return
     */
    public static Strategy getNewStrategy(String type){
        if ("skip".equals(type)){
            return new SkipStrategy();
        }else if ("hit".equals(type)){
            return new HitStrategy();
        }

        return null;
    }

}
