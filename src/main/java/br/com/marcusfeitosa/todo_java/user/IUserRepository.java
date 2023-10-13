package br.com.marcusfeitosa.todo_java.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserDTO, UUID> {
    UserDTO findByUsername(String username);


}
