package com.example.blog.service;

import com.example.blog.entity.TBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blog.utils.RespBean;
import com.example.blog.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Yang
 * @since 2023-03-11
 */
public interface TBLOGervice extends IService<TBlog> {
    //分页查询
    RespBean pageBlogs(Long current, Long limit, Boolean published, String flag, Boolean share_statement, Boolean is_delete);

    //根据博客标题查询
    RespBean getByTitle(String title);

    Result getByTitle1(String title);

}
