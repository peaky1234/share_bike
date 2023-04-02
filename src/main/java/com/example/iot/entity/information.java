package com.example.iot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class information {
    @TableId(type = IdType.AUTO) //数据库如果是没有勾选自增 默认是雪花算法 给id自动配值  如果勾选了 必须要加上该语句
    private Integer id;

    private String type;
    private LocalDateTime time;
    private String factory;
    private Integer repairs;
    private String model;
    @TableField(value = "cardID")
    private String cardID; //卡号
//    @TableField(value = "myID")
//    private String myID;  //自己定义的卡号
}
