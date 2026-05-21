package com.fastproject.module.security.filter;

import com.fastproject.props.SecurityProps;
import com.fastproject.utils.TokenUtils;
import com.fastproject.vo.SecurityUserVo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter  extends OncePerRequestFilter {


    private final TokenUtils tokenUtils;
    private final SecurityProps securityProps;

    public JwtAuthenticationFilter(TokenUtils tokenUtils, SecurityProps securityProps) {
        this.tokenUtils = tokenUtils;
        this.securityProps = securityProps;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // 获取当前用户
            SecurityUserVo user = tokenUtils.getUser();
            if (user != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.warn("Jwt token 校验失败: " + e.getMessage());
        }

        // 放行
        filterChain.doFilter(request, response);
    }
}
