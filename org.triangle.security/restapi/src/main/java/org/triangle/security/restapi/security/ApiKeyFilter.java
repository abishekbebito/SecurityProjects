package org.triangle.security.restapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.triangle.security.restapi.constant.SecurityConstant.*;

@Component
@Slf4j
public class ApiKeyFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path=request.getRequestURI();
        log.info("path passed in filter:"+path);
        if(path.startsWith("/public")){
            filterChain.doFilter(request,response);
            log.info("public-api");
            return;
        }
        String requestHeader=request.getHeader(API_KEY_HEADER);

        String apiprivateKey = System.getenv(API_KEY_ENV);
        log.info(apiprivateKey+":"+requestHeader);

        if(requestHeader == null || !requestHeader.equals(apiprivateKey)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(UNAUTHORIZED_MESSAGE);
            log.info("private-api:");
            return;
        }
        Authentication authn = new PreAuthenticatedAuthenticationToken(
                "api-client-123",   // principal = API client identity
                null,
                List.of(new SimpleGrantedAuthority("ROLE_API"))
        );
        SecurityContextHolder.getContext().setAuthentication(authn);
        filterChain.doFilter(request,response);
    }
}
