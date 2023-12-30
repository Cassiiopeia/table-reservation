package com.suh.tablereservation.config.filter;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.service.PartnerService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@WebFilter(urlPatterns = "/partner")
@RequiredArgsConstructor
public class PartnerFilter implements Filter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PartnerService partnerService;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        if (!jwtAuthenticationProvider.validateToken(token)) {
            throw new ServletException("Invalid Access!");
        }
        UserDto userDto = jwtAuthenticationProvider.getUserDto(token);
        partnerService.findByIdAndEmail(userDto.getId(), userDto.getEmail())
                .orElseThrow(() -> new ServletException("Invalid Access"));
        chain.doFilter(request, response);
    }
}