package com.example.iot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.iot.entity.R;
import com.example.iot.entity.information;
import com.example.iot.service.informationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("user")
public class informationController {
    @Autowired
    private informationService informationAto;
   //查询数据后面三个变量就是多字段查询    没有值就是所有的信息
    @GetMapping("/findAll")
    public R<List<information>> FindAll(Integer page,Integer limit, String cardID,
                                         String factory,
                                         String type ){

        LambdaQueryWrapper<information> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(information::getTime);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(cardID),information::getCardID,cardID);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(factory),information::getFactory,factory);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(type),information::getType,type);
        int count=informationAto.count(lambdaQueryWrapper);
        List<information> list=informationAto.list(lambdaQueryWrapper);
        if(list.isEmpty()){
            return R.error("查询不到相关数据");
        }
        else {

            return R.list(list, count);
        }
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody information information1){
          information1.setTime(LocalDateTime.now());
          informationAto.save(information1);
          return R.success("添加成功");
    }

    //删除一行或者多行
    @DeleteMapping("/delete")
    public R<String> delete(@RequestBody List<information> information1){
        information1.forEach(item->{
            log.info(String.valueOf(item.getId()));
            informationAto.removeById(item.getId());
        });

        return R.success("删除成功");
    }

    @PostMapping("/update")
    public R<String> update(@RequestBody information information1){
        informationAto.updateById(information1);
        return R.success("编辑成功");
    }



}
