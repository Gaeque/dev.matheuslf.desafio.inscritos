package dev.matheuslf.desafio.inscritos.services;

import dev.matheuslf.desafio.inscritos.dto.projectDTO.ProjectRequestDTO;
import dev.matheuslf.desafio.inscritos.dto.projectDTO.ProjectResponseDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.entity.Users;
import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import dev.matheuslf.desafio.inscritos.mapper.ProjectMapper;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UsersRepository usersRepository;

    public ProjectService(ProjectRepository projectRepository,  ProjectMapper projectMapper, UsersRepository usersRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.usersRepository = usersRepository;
    }

    public ProjectResponseDTO saveProject(ProjectRequestDTO requestDTO) {
        Project project = new Project();
        project.setName(requestDTO.getName());
        project.setDescription(requestDTO.getDescription());
        project.setStartDate(requestDTO.getStartDate());
        project.setEndDate(requestDTO.getEndDate());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail;

        if (authentication.getPrincipal() instanceof String) {
            userEmail = (String) authentication.getPrincipal();
        } else {
            userEmail = authentication.getName();
        }

        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        project.setUser(user);

        Project saved = projectRepository.save(project);
        return projectMapper.toDTO(saved);
    }

    public List<ProjectResponseDTO> searchProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toDTOList(projects);
    }

    public ProjectResponseDTO finishProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto com id " + id + " não encontrado."));

        boolean hasOpenTasks = project.getTasks().stream()
                .anyMatch(task -> task.getStatus() != StatusEnum.DONE);

        if (hasOpenTasks) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Projeto ainda possui tarefas abertas.");
        }

        project.setEndDate(LocalDate.now());

        Project updated = projectRepository.save(project);
        return projectMapper.toDTO(updated);
    }

}
