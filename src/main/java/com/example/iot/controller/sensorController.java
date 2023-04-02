package com.example.iot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.iot.entity.R;
import com.example.iot.entity.Temp;
import com.example.iot.entity.information;
import com.example.iot.entity.recording;
import com.example.iot.service.recordService;
import com.example.iot.service.sensorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("sensor")
public class sensorController {
    @Autowired
    sensorService sensor;

    @GetMapping("/sensorFind")
    public R<List<Temp>> sensorFind(Integer page, Integer limit, String type,
                                    String status,String number){
        LambdaQueryWrapper<Temp> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(type),Temp::getType,type);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(number),Temp::getNumber,number);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(status),Temp::getStatus,status);

        int count = sensor.count(lambdaQueryWrapper);
        return R.list(sensor.list(lambdaQueryWrapper),count);
    }

    @DeleteMapping("/sensorDelete")
    public R<String> delete(@RequestBody List<information> information1){
        information1.forEach(item->{
            log.info(String.valueOf(item.getId()));
            sensor.removeById(item.getId());
        });

        return R.success("删除成功");
    }
}
