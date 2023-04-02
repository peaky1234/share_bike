package com.example.iot.controller;

import com.example.iot.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class clearControl {

    int flag=1;

    @PostMapping("/clear")
    public R<String> clear(){
          if(flag==1){
              flag=0;
              return R.success("清除");
          }
          return R.error("刷新不能清除");
    }
}
