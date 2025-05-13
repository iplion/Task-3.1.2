package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itmentor.spring.boot_security.demo.security.UserDetailsImpl;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/users");
        } else if (roles.contains("ROLE_USER")) {
            UUID uuid = ((UserDetailsImpl) authentication.getPrincipal()).getUuid();
            httpServletResponse.sendRedirect("/user/" + uuid);
        } else {
            httpServletResponse.sendRedirect("/");
        }
    }
}