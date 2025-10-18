package dev.matheuslf.desafio.inscritos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheuslf.desafio.inscritos.dto.taskDTO.TaskRequestDTO;
import dev.matheuslf.desafio.inscritos.dto.taskDTO.TaskStatusRequestDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.entity.Users;
import dev.matheuslf.desafio.inscritos.enums.PriorityEnum;
import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import dev.matheuslf.desafio.inscritos.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Project project;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
        projectRepository.deleteAll();
        usersRepository.deleteAll();

        Users user = new Users();
        user.setName("teste");
        user.setEmail("teste@email.com");
        user.setPassword("123456");
        usersRepository.save(user);


        project = new Project();
        project.setName("Projeto Teste");
        project.setDescription("Descrição do projeto");
        project.setUser(user);
        project = projectRepository.save(project);
    }

    @Test
    void deveCriarTarefaComSucesso() throws Exception {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Implementar Login");
        requestDTO.setDescription("Sistema de autenticação");
        requestDTO.setStatus(StatusEnum.TODO);
        requestDTO.setPriority(PriorityEnum.HIGH);
        requestDTO.setDueDate(LocalDate.now().plusDays(7));
        requestDTO.setProjectId(project.getId());

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Implementar Login"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void deveRetornar400QuandoTituloVazio() throws Exception {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("");  // título vazio
        requestDTO.setDescription("Descrição");
        requestDTO.setStatus(StatusEnum.TODO);
        requestDTO.setPriority(PriorityEnum.LOW);
        requestDTO.setProjectId(project.getId());

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveListarTarefas() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    void deveAtualizarStatusDaTarefa() throws Exception {
        // Criar uma tarefa primeiro
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Tarefa para atualizar");
        requestDTO.setStatus(StatusEnum.TODO);
        requestDTO.setPriority(PriorityEnum.MEDIUM);
        requestDTO.setProjectId(project.getId());

        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andReturn().getResponse().getContentAsString();

        Long taskId = objectMapper.readTree(response).get("id").asLong();

        TaskStatusRequestDTO statusRequest = new TaskStatusRequestDTO();
        statusRequest.setStatus(StatusEnum.DOING);

        mockMvc.perform(put("/tasks/" + taskId + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DOING"));
    }

    @Test
    void deveDeletarTarefa() throws Exception {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Tarefa para deletar");
        requestDTO.setStatus(StatusEnum.TODO);
        requestDTO.setPriority(PriorityEnum.LOW);
        requestDTO.setProjectId(project.getId());

        String response = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andReturn().getResponse().getContentAsString();

        Long taskId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/tasks/" + taskId))
                .andExpect(status().isOk());
    }
}