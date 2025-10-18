package dev.matheuslf.desafio.inscritos.services;

import dev.matheuslf.desafio.inscritos.dto.authDTO.LoginRequestDTO;
import dev.matheuslf.desafio.inscritos.dto.authDTO.LoginResponseDTO;
import dev.matheuslf.desafio.inscritos.dto.authDTO.RegisterRequestDTO;
import dev.matheuslf.desafio.inscritos.entity.Users;
import dev.matheuslf.desafio.inscritos.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository UsersRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UsersRepository UsersRepository, JwtService jwtService) {
        this.UsersRepository = UsersRepository;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequestDTO requestDTO) {
        if (UsersRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Users user = new Users();
        user.setEmail(requestDTO.getEmail());
        user.setName(requestDTO.getName());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        UsersRepository.save(user);

        return "Usuário cadastrado com sucesso";
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        Optional<Users> userOptional = UsersRepository.findByName(requestDTO.getName());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Nome ou senha inválidos");
        }

        Users user = userOptional.get();
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("E-mail ou senha inválidos");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponseDTO(token);
    }

}
