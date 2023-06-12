package com.example.blog.service.impl;

import com.example.blog.entity.TUser;
import com.example.blog.mapper.TUserMapper;
import com.example.blog.service.TUSERervice;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yang
 * @since 2023-03-11
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUSERervice {

}
