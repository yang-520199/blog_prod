package com.example.blog.controller;


import com.example.blog.service.TBLOGervice;
import com.example.blog.service.impl.TBlogServiceImpl;
import com.example.blog.utils.RespBean;
import com.example.blog.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RestController;

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
public class TBlogController {
    @Autowired
    TBLOGervice tbloGervice;

    RespBean respBean = RespBean.build();

    /**
     * 分页查询
     * @param current
     * @param size
     * @param published
     * @param flag
     * @param share_statement
     * @param is_delete
     * @return
     */
    @GetMapping("/getByPage")
    @ApiOperation("博客分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页的数量"),
            @ApiImplicitParam(name = "published",value = "是否公开"),
            @ApiImplicitParam(name = "flag",value = "原创或转载"),
            @ApiImplicitParam(name = "share_statement",value = "草稿"),
            @ApiImplicitParam(name = "is_delete",value = "是否已删除"),
    })
    public RespBean getByPage(Long current, Long size, Boolean published,
                              String flag, Boolean share_statement, Boolean is_delete){
        return tbloGervice.pageBlogs(current, size,published,flag,share_statement,is_delete);
    }

    /**
     * 根据博客标题查询
     * @param title
     * @return
     */
    @GetMapping("/getByTitle")
    @ApiOperation("通过文章标题查询")
    @ApiImplicitParam(name = "title",value = "文章的标题")

    public Result getByTitle(String title){
        return tbloGervice.getByTitle1(title);

    }

}
