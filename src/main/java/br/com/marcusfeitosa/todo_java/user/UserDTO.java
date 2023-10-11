package br.com.marcusfeitosa.todo_java.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_users")
public class UserDTO {
    @Id
    @GeneratedValue(generator = "UUID" )
    private UUID id;

    private String name;
    private String username;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
