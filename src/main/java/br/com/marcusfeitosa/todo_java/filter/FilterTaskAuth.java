package br.com.marcusfeitosa.todo_java.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.marcusfeitosa.todo_java.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        var serveletPath = request.getServletPath();
        System.out.println(serveletPath);
        if(serveletPath.startsWith("/tasks/")){
            var authorization = request.getHeader("Authorization");
            var encryptedUserPassword = authorization.substring("Basic".length()).trim();
            byte[] decryptedUserPassword = Base64.getDecoder().decode(encryptedUserPassword);
            var decryptedUserPasswordString = new String(decryptedUserPassword);
            String[] userCredentials = decryptedUserPasswordString.split(":");
            String username = userCredentials[0];
            String password = userCredentials[1];

            var databaseUsername = this.userRepository.findByUsername(username);

            if( databaseUsername == null){
                response.sendError(401, "Nome de Usuário não encontrado");
            }else{
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), databaseUsername.getPassword());
                if(passwordVerify.verified){
                    request.setAttribute("idUser", databaseUsername.getId());
                    filterChain.doFilter(request, response);
                }else{
                    response.sendError(401);
                }
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

}
