package com.crcc.test.config;

import com.crcc.test.system.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SpringBoot + Spring Security 学习笔记（一）自定义基本使用及个性化登录配置
 * https://www.hellojava.com/a/75399.html
 * Created by dell on 2019/7/11.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity.authorizeRequests().antMatchers("/**").permitAll();
//        httpSecurity.formLogin()               //  定义当需要提交表单进行用户登录时候，转到的登录页面。
//                .and()
//                .authorizeRequests()   // 定义哪些URL需要被保护、哪些不需要被保护
//                .anyRequest()          // 任何请求,登录后可以访问
//                .authenticated();
//    }
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 3.自定义安全认证配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                                // 定义当需要用户登录时候，转到的登录页面。
                .loginPage("/login")                        // 设置登录页面
                .loginProcessingUrl("/user/login")          // 自定义的登录接口
                .successHandler(myAuthenctiationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)   //注意：defaultSuccessUrl不需要再配置了，实测如果配置了，成功登录的 handler 就不起作用了。
//                .defaultSuccessUrl("/home").permitAll()     // 登录成功之后，默认跳转的页面
                .and().authorizeRequests()                  // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/", "/index","/user/login").permitAll()       // 设置所有人都可以访问登录页面
                .anyRequest().authenticated()               // 任何请求,登录后可以访问
                .and().csrf().disable();                    // 关闭csrf防护
    }

    /**
     * 静态资源忽略配置
     * 3的配置会发现资源文件也被安全框架挡在了外面，因此需要进行安全配置
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**/*", "/**/*.css", "/**/*.js");
    }
}
