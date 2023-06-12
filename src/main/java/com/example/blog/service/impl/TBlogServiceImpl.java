package com.example.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.entity.TBlog;
import com.example.blog.mapper.TBlogMapper;
import com.example.blog.service.TBLOGervice;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blog.utils.RespBean;
import com.example.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yang
 * @since 2023-03-11
 */
@Service
public class TBlogServiceImpl extends ServiceImpl<TBlogMapper, TBlog> implements TBLOGervice {
    @Autowired
    TBlogMapper tBlogMapper;

    RespBean respBean=RespBean.build();

    @Override
    public RespBean pageBlogs(Long current, Long limit, Boolean published, String flag, Boolean share_statement, Boolean is_delete) {
        RespBean respBean=RespBean.build();
        //创建page对象
        Page<TBlog> tBlogPage=new Page<>(current,limit);
        //构建条件
        QueryWrapper<TBlog> wrapper=new QueryWrapper<>();
        //获取传入讲师的条件是否为空
        //多条件组合查询
        //判断条件值是否为空,如果不为空拼接条件
        wrapper.eq(published!=null,"published",published);
        wrapper.eq(flag!=null,"flag",flag);
        wrapper.eq(share_statement!=null,"share_statement",share_statement);
        wrapper.eq(is_delete!=null,"is_delete",is_delete);
        //以更新时间排序
        wrapper.orderByDesc("update_time");
        //调用mybatis plus分页方法进行查询
        tBlogMapper.selectPage(tBlogPage,wrapper);
        //通过Page对象获取分页信息
        List<TBlog> tBlogList=tBlogPage.getRecords();
        long size = tBlogPage.getSize(); //每页显示的条数
        long total = tBlogPage.getTotal(); //总记录数
        long pages = tBlogPage.getPages(); //总页数
        if (tBlogList.size()!=0){
            respBean.setMsg("成功");
            respBean.setStatus(200);
            respBean.setObj(tBlogPage);
        }
        return respBean;
    }

    @Override
    public RespBean getByTitle(String title) {
        RespBean respBean=RespBean.build();

        return null;
    }

    @Override
    public Result getByTitle1(String title) {
        Result result=new Result();
        QueryWrapper<TBlog> wrapper=new QueryWrapper<TBlog>();
        wrapper.like("title",title);
        List<TBlog> tBlogList=tBlogMapper.selectList(wrapper);
        if (tBlogList.size()!=0){

//            result.setData(tBlogList);
            result=Result.success("查询成功",tBlogList);
        }
        return result;
    }
}
