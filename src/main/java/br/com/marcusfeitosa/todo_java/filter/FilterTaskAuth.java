package br.com.marcusfeitosa.todo_java.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       var authorization = request.getHeader("Authorization");
       var encryptedUserPassword = authorization.substring("Basic".length()).trim();
       byte[] decryptedUserPassword = Base64.getDecoder().decode(encryptedUserPassword);
       var decryptedUserPasswordString = new String(decryptedUserPassword);
       String[] userCredentials = decryptedUserPasswordString.split(":");
       String username = userCredentials[0];
       String password = userCredentials[1];

        filterChain.doFilter(request, response);
    }
}
