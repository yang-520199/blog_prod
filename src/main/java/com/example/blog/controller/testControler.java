package com.example.blog.controller;

import com.example.blog.entity.TUser;
import com.example.blog.mapper.TUserMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin
public class testControler {
    @Resource
    TUserMapper tUserMapper;
    @RequestMapping("/test")
    public TUser hello(){
        TUser tUser=tUserMapper.loadUserByUsername("admin");
        return tUser;
    }
}
