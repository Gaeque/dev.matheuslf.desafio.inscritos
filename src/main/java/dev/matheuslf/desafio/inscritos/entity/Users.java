package dev.matheuslf.desafio.inscritos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Seu nome é obrigatório.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Senha é obrigatório")
    @Column(nullable = false)
    private String password;


}
