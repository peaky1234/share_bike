package com.example.iot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.iot.entity.recording;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface recordMapper extends BaseMapper<recording> {
}
