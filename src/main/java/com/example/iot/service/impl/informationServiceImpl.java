package com.example.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.iot.entity.information;
import com.example.iot.mapper.informationMapper;
import com.example.iot.service.informationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public  class informationServiceImpl extends ServiceImpl<informationMapper, information> implements informationService {

    @Override
    public String  sel_num(String num, String type1) {
        LambdaQueryWrapper<information> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(num),information::getCardID,num);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(type1),information::getType,type1);
        information infor=this.getOne(lambdaQueryWrapper);
        if(infor==null){
            return null;
        }
        return String.valueOf(infor.getId());
    }

    public void addRepair(Integer num){
        LambdaUpdateWrapper<information> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(information::getId,num).setSql("repairs=repairs+1");//setSql方法允许设置SQL语句片段，
        this.update(null, wrapper);
    }
}
