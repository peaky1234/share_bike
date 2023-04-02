package com.example.iot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iot.entity.Temp;
import com.example.iot.mapper.sensorMapper;
import com.example.iot.service.sensorService;
import org.springframework.stereotype.Service;

@Service
public class sensorServiceImpl extends ServiceImpl<sensorMapper, Temp> implements sensorService {
}
