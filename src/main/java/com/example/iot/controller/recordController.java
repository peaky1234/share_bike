package com.example.iot.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.iot.entity.R;
import com.example.iot.entity.information;
import com.example.iot.entity.recording;
import com.example.iot.service.recordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("record")
public class recordController {

    @Autowired
    recordService record;

    @GetMapping("/recordFind")
    public R<List<recording>> recordFind(Integer page,Integer limit,String username,
                                         String number){
        LambdaQueryWrapper<recording> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(username),recording::getUsername,username);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(number),recording::getNumber,number);
        List<recording> list=record.list(lambdaQueryWrapper);
        int count = record.count(lambdaQueryWrapper);

        return R.list(list,count);
    }

    @DeleteMapping("/recordDelete")
    public R<String> delete(@RequestBody List<information> information1){
        information1.forEach(item->{
            log.info(String.valueOf(item.getId()));
            record.removeById(item.getId());
        });

        return R.success("删除成功");
    }
}
