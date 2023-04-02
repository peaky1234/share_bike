package com.example.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iot.entity.recording;
import com.example.iot.mapper.recordMapper;
import com.example.iot.service.recordService;
import org.springframework.stereotype.Service;

@Service
public class recordServiceImpl extends ServiceImpl<recordMapper, recording> implements recordService {
}
