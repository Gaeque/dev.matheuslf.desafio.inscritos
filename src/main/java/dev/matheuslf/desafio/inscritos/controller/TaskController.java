package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.taskDTO.TaskRequestDTO;
import dev.matheuslf.desafio.inscritos.dto.taskDTO.TaskResponseDTO;
import dev.matheuslf.desafio.inscritos.dto.taskDTO.TaskStatusRequestDTO;
import dev.matheuslf.desafio.inscritos.enums.PriorityEnum;
import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import dev.matheuslf.desafio.inscritos.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @Operation(
            summary = "Criar nova tarefa",
            description = "Cria uma nova tarefa vinculada a um projeto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Tarefa criada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Projeto não encontrado"
            )
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> saveTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
       TaskResponseDTO created = taskService.saveTask(taskRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Buscar tarefas com filtros",
            description = "Retorna lista de tarefas filtradas por status, prioridade e/ou projeto"
    )
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> searchTasks(
            @RequestParam(required = false) StatusEnum status,
            @RequestParam(required = false) PriorityEnum priority,
            @RequestParam(required = false) Long projectId
    ) {
        List<TaskResponseDTO> tasks = taskService.searchTasks(status, priority, projectId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @Operation(
            summary = "Atualizar status da tarefa",
            description = "Atualiza apenas o status de uma tarefa específica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Status inválido"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusRequestDTO taskStatusRequestDTO) {

        TaskResponseDTO updatedTask = taskService.updateTask(id, taskStatusRequestDTO.getStatus());
        return ResponseEntity.ok(updatedTask);
    }

    @Operation(
            summary = "Deletar tarefa",
            description = "Remove uma tarefa do sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada!");
    }

}
