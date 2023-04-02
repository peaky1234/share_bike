package com.example.iot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Temp {
     @TableId(type = IdType.AUTO)
     Integer id;
     String time;
     String value;
     String type;
     String number;
     Integer status;
}
