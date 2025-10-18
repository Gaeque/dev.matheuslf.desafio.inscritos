package dev.matheuslf.desafio.inscritos.controller;

import dev.matheuslf.desafio.inscritos.dto.projectDTO.ProjectRequestDTO;
import dev.matheuslf.desafio.inscritos.dto.projectDTO.ProjectResponseDTO;
import dev.matheuslf.desafio.inscritos.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projetos", description = "Endpoints para gerenciamento de projetos")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(
            summary = "Criar novo projeto",
            description = "Cria um novo projeto no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Projeto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProjectResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            )
    })
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        ProjectResponseDTO createdProject = projectService.saveProject(projectRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }


    @Operation(
            summary = "Listar todos os projetos",
            description = "Retorna uma lista com todos os projetos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de projetos retornada com sucesso"
    )
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getProjects() {
        List<ProjectResponseDTO> projects = projectService.searchProjects();
        return ResponseEntity.ok(projects);
    }

    @Operation(
            summary = "Finalizar projeto",
            description = "Finaliza o projeto caso todas as tarefas estejam concluídas ('DONE')"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto finalizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Projeto ainda possui tarefas abertas"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ProjectResponseDTO> finalizarProjeto(@PathVariable Long id) {
        ProjectResponseDTO projectFinalizado = projectService.finishProject(id);
        return ResponseEntity.ok(projectFinalizado);
    }

}
