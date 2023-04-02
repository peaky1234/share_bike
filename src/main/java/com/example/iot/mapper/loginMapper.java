package com.example.iot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.iot.entity.employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface loginMapper extends BaseMapper<employee> {
}
