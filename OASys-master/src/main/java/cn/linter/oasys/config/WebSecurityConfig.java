package cn.linter.oasys.config;

import cn.linter.oasys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/setAttendanceTime").hasAnyRole("主管","经理")
                .antMatchers("/api/**").authenticated()
                .and().formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\":\"success\",\"message\":\"Login succeed!\"}");
                    out.flush();
                    out.close();
                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\":\"error\",\"message\":\"User name or psd is wrong!\"}");
                    out.flush();
                    out.close();
                })
                .and().exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\":\"error\",\"message\":\"Auth is out of date,please login again。\"}");
                    out.flush();
                    out.close();
                })
                .accessDeniedHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\":\"error\",\"message\":\"You have no privilege!\"}");
                    out.flush();
                    out.close();
                })
                .and().logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\":\"success\",\"message\":\"Sign out succeed!\"}");
                    out.flush();
                    out.close();
                })
                .and().userDetailsService(userService)
                .csrf() .disable();
    }
}