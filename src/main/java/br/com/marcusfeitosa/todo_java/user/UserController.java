package br.com.marcusfeitosa.todo_java.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController  {

    @Autowired
    private IUserRepository userRepository;
    @PostMapping
    public ResponseEntity createNewUser(@RequestBody UserDTO userDTO){
        var user = this.userRepository.findByUsername(userDTO.getUsername());
        var encryptedPassword = BCrypt.withDefaults().hashToString(12, userDTO.getPassword().toCharArray());
        userDTO.setPassword(encryptedPassword);
        if(user != null){
            throw new Error("Nome de usuário já cadastrado");
        }else {
            this.userRepository.save(userDTO);
            return ResponseEntity.ok().build();
        }
    }
}
