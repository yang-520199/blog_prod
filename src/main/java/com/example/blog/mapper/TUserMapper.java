package com.example.blog.mapper;

import com.example.blog.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yang
 * @since 2023-03-11
 */
public interface TUserMapper extends BaseMapper<TUser> {

    TUser loadUserByUsername(String userName);
}
