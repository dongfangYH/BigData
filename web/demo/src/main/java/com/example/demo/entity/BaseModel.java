package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description BaseEntity Model
 * @Author yuanhang.liu@tcl.com
 * @Date 2019-09-26
 **/
@Data
public class BaseModel<T extends Model> extends Model {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Long version;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
