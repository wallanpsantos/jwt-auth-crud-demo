package com.jwtauthcruddemo.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/***
 * Classe de configuração HTTP para interceptar
 * as solicitações recebidas e validar o JWT
 */
public class JWTAuthFilter extends OncePerRequestFilter {

    private final LoginAuthProvider loginAuthProvider;

    public JWTAuthFilter(LoginAuthProvider loginAuthProvider) {
        this.loginAuthProvider = loginAuthProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Verificar se existe um cabeçalho de autorização
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Portador
        if (Objects.nonNull(header)) validateAndSetAuthentication(header, request);

        // Continue a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private void validateAndSetAuthentication(String header, HttpServletRequest request) {
        String[] authElements = header.split(" ");
        if (authElements.length == 2 && "Bearer".equals(authElements[0])) {
            try {
                processTokenAuthentication(request, authElements);
            } catch (RuntimeException ex) {
                SecurityContextHolder.clearContext();
                throw ex;
            }
        }
    }

    private void processTokenAuthentication(HttpServletRequest request, String[] authElements) {
        if (Objects.equals(HttpMethod.GET, request.getMethod())) {
            SecurityContextHolder.getContext().setAuthentication(loginAuthProvider.validateToken(authElements[1]));
        } else {
            SecurityContextHolder.getContext().setAuthentication(loginAuthProvider.validateTokenStrongly(authElements[1]));
        }
    }
}
