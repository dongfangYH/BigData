package com.example.demo.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description
 * @Author yuanhang.liu@tcl.com
 * @Date 2019-09-26
 **/
@Slf4j
@Component
public class EntityMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject, FieldFill.INSERT);
        this.setFieldValByName("deleted", 1, metaObject, FieldFill.INSERT);
        this.setFieldValByName("updateTime", new Date(), metaObject, FieldFill.INSERT);
        this.setFieldValByName("version", 1L, metaObject, FieldFill.INSERT);
        this.setFieldValByName("deleted", 0, metaObject, FieldFill.INSERT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject, FieldFill.UPDATE);
    }
}