package com.example.blog.controller;


import com.example.blog.entity.TUser;
import com.example.blog.mapper.TUserMapper;
import com.example.blog.service.impl.TUserServiceImpl;
import com.example.blog.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Yang
 * @since 2023-03-11
 */
@RestController
@CrossOrigin
public class TUserController {
    @Resource
    TUserMapper tUserMapper;
    @Autowired
    TUserServiceImpl tUserService;
    @GetMapping("/hello")
    public RespBean test(){
        TUser user=tUserMapper.loadUserByUsername("admin");
        return RespBean.ok("用户："+user.getUsername()+"登录成功！");
    }

}
