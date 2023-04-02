package com.example.iot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class employee {
    @TableId(type = IdType.AUTO)
    Integer id;
    String username;
    String password;
    String phone;
    String status;
    String post;//职位
}
