package com.example.blog.config;


import com.example.blog.entity.TUser;
import com.example.blog.service.impl.CustomUserServiceImpl;
import com.example.blog.utils.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    CustomUserServiceImpl customUserService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //要有一个configure方法把hrService整进来
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(customUserService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable();
        httpSecurity.cors();
        httpSecurity.authorizeRequests()
                .antMatchers("/getByPage").permitAll()
//                .antMatchers().permitAll()
//                .antMatchers("/blog/vuefindByPage","/blog/getByBlogId",
//                        "/type/getAllType","/tag/getAllTag","/blog/getByTitle",
//                        "/comment/comments/{blogId}","/comment/comments",
//                        "/blog/countBlog","/comment/getCommentByPage",
//                        "/links/getAllLink","/blog/vuefindHotBlog").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
                        httpServletResponse.addHeader("Access-Control-Allow-Method","POST,GET");
                        //如果登录成功就返回一段json
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        //这是往出写
                        PrintWriter out =httpServletResponse.getWriter();
                        //登录成功的对象
                        TUser tUser= (TUser) authentication.getPrincipal();
                        //为了安全考虑，这里返回给前端时密码返回null
                        tUser.setPassword(null);
                        RespBean ok=RespBean.ok("登录成功！",tUser);
                        //把user写成字符串
                        String s=new ObjectMapper().writeValueAsString(ok);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
                        httpServletResponse.addHeader("Access-Control-Allow-Method","POST,GET");
                        //如果登录失败也返回i一段json
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        //设置状态码
                        //resp.setStatus(401);

                        //这是往出写的
                        PrintWriter out=httpServletResponse.getWriter();
                        RespBean respBean=RespBean.error("登录失败！");
                        if (e instanceof LockedException){
                            respBean.setMsg("账户被锁定，请联系管理员！");
                        }else if (e instanceof CredentialsExpiredException){
                            respBean.setMsg("密码过期，请联系管理员！");
                        }else if (e instanceof DisabledException){
                            respBean.setMsg("账户被禁用，请联系管理员！");
                        }else if (e instanceof BadCredentialsException){
                            respBean.setMsg("用户名或者密码输入错误，请重新输入！");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");
                        httpServletResponse.addHeader("Access-Control-Allow-Method","POST,GET");
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out=httpServletResponse.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable();
    }


}
