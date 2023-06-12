package com.example.blog.service.impl;

import com.example.blog.entity.TUser;
import com.example.blog.mapper.TUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;

@Service
@CrossOrigin(origins = "*")
public class CustomUserServiceImpl implements UserDetailsService {
    @Resource
    TUserMapper tUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TUser tUser=tUserMapper.loadUserByUsername(s);
        if (tUser.getUsername()==null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //用户存在的话就返回hr对象
        String a=new BCryptPasswordEncoder().encode(tUser.getPassword());
        System.out.println(tUser.getPassword());
        //密码解密
        boolean matches= new BCryptPasswordEncoder().matches("123",tUser.getPassword());
        System.out.println(a);
        System.out.println(matches);
//        tUser.setPassword(a);
        return tUser;
    }
}
