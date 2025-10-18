package dev.matheuslf.desafio.inscritos.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor Local")
                ))
                .info(new Info()
                        .title("API de Gerenciamento de Tarefas")
                        .version("1.0.0")
                        .description("""
                                API REST para gerenciamento de projetos e tarefas.

                                **Autenticação:**
                                - Endpoints de login e registro: `/auth/register`, `/auth/login` (sem token)
                                - Endpoints de projetos e tarefas: requerem token JWT no cabeçalho `Authorization`

                                **Funcionalidades:**
                                - Criação e gerenciamento de projetos
                                - Criação e gerenciamento de tarefas
                                - Filtros avançados para busca
                                - Atualização de status de tarefas
                                - Finalização de projetos
                                """)
                        .contact(new Contact()
                                .name("Gaeque")
                                .email("Gaeque.CF@hotmail.com")
                                .url("https://github.com/Gaeque"))
                );
    }
}