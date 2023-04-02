package com.example.iot.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.iot.entity.R;
import com.example.iot.entity.employee;
import com.example.iot.entity.information;
import com.example.iot.service.loginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("admin")
public class loginControl {
    @Autowired
    loginService login;

    @PostMapping("/login")
    public R<String> main(@RequestBody employee employees, HttpServletRequest httpServletRequest){
        LambdaQueryWrapper<employee> lambdaQueryWrapper= new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(employees.getUsername()),employee::getUsername,employees.getUsername());
        employee em=login.getOne(lambdaQueryWrapper);


        if(em==null)
            return R.error("账号不存在");
        else{

            lambdaQueryWrapper.eq(StringUtils.isNotEmpty(employees.getPassword()),employee::getPassword,employees.getPassword());
            em=login.getOne(lambdaQueryWrapper);
            if(em==null) return R.error("密码错误");
            else{
                if(em.getPost().equals("用户"))
                    return R.error("不是管理员");
                else if(em.getStatus().equals("禁用"))
                    return R.error("该账号已被禁用");
                else
                {
                    httpServletRequest.getSession().setAttribute("loginInfo",em.getId());
                    return R.success(em.getUsername());
                }
            }
        }
    }

    @GetMapping("loginout")
    public  R<String>  loginOut(HttpServletRequest httpServletRequest){
         httpServletRequest.getSession().removeAttribute("loginInfo");
         return R.success("退出成功");
    }

    @GetMapping("/employeeFind")
    public R<List<employee>> FindAll(Integer page, Integer limit, String username,
                                        String password){

        LambdaQueryWrapper<employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(username),employee::getUsername,username);
        lambdaQueryWrapper.eq(StringUtils.isNotEmpty(password),employee::getPassword,password);
        int count=login.count(lambdaQueryWrapper);
        List<employee> list=login.list(lambdaQueryWrapper);
        if(list.isEmpty()){
            return R.error("查询不到相关数据");
        }
        else {

            return R.list(list, count);
        }
    }

    @PostMapping("/employeeAdd")
    public R<String> add(@RequestBody employee employee){
        login.save(employee);
        return R.success("添加成功");
    }

    @DeleteMapping("/employeeDelete")
    public R<String> delete(@RequestBody List<employee> employees){
        employees.forEach(item->{
            log.info(String.valueOf(item.getId()));
            login.removeById(item.getId());
        });

        return R.success("删除成功");
    }

    @PostMapping("/employeeUpdate")
    public R<String> update(@RequestBody employee employees){
        login.updateById(employees);
        return R.success("编辑成功");
    }
}

