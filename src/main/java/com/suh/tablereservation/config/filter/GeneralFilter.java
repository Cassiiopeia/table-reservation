package com.suh.tablereservation.config.filter;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.domain.common.UserType;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
@RequiredArgsConstructor
public class GeneralFilter implements Filter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 회원가입, 로그인, 키오스크 방문인증시 -> Token 검사하지않음
    private final List<String> excludedUrls = List.of("/signup/*", "/signin/*","kiosk/*");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length()).replaceAll("[/]+$", "");

        boolean isExcluded = excludedUrls.stream().anyMatch(path::matches);

        if (!isExcluded) {
            String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
            jwtAuthenticationProvider.validateToken(token);

            UserDto userDto = jwtAuthenticationProvider.getUserDto(token);
            if (path.startsWith("/customer") && userDto.getUserType() != UserType.CUSTOMER) {
                throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
            }

            if (path.startsWith("/partner") && userDto.getUserType() != UserType.PARTNER) {
                throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
            }
        }
        chain.doFilter(request, response);
    }
}