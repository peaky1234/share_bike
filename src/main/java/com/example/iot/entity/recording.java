package com.example.iot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class recording {
    @TableId(type = IdType.AUTO)
    Integer id;
    String start;
    String end;
    String username;
    String number;
    Integer cost;
    Integer repair;
}
