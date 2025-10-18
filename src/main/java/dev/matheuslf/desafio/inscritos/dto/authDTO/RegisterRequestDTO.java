package dev.matheuslf.desafio.inscritos.dto.authDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String name;
   private String email;
   private String password;
}
