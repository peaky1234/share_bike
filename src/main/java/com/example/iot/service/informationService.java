package com.example.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.iot.entity.information;

public interface informationService extends IService<information> {

    String sel_num(String num, String type1);

    void addRepair(Integer num);
}
