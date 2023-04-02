package com.example.iot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iot.mapper.loginMapper;
import com.example.iot.service.loginService;
import org.springframework.stereotype.Service;
import com.example.iot.entity.employee;

@Service
public class loginServiceImpl extends ServiceImpl<loginMapper,employee> implements loginService {
}
