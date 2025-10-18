package dev.matheuslf.desafio.inscritos.services;

import dev.matheuslf.desafio.inscritos.dto.taskDTO.TaskRequestDTO;
import dev.matheuslf.desafio.inscritos.dto.taskDTO.TaskResponseDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import dev.matheuslf.desafio.inscritos.entity.Task;
import dev.matheuslf.desafio.inscritos.enums.PriorityEnum;
import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import dev.matheuslf.desafio.inscritos.mapper.TaskMapper;
import dev.matheuslf.desafio.inscritos.repository.ProjectRepository;
import dev.matheuslf.desafio.inscritos.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO requestDTO) {
        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Task task = new Task();
        task.setTitle(requestDTO.getTitle());
        task.setDescription(requestDTO.getDescription());
        task.setStatus(requestDTO.getStatus());
        task.setPriority(requestDTO.getPriority());
        task.setDueDate(requestDTO.getDueDate());
        task.setProject(project);

        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    public List<TaskResponseDTO> searchTasks(StatusEnum status, PriorityEnum priority, Long projectId) {
        List<Task> tasks;

        if (status == null && priority == null && projectId == null) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findByFilters(status, priority, projectId);
        }

        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO updateTask(Long taskId, StatusEnum newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task com id " + taskId + " não encontrado."));

        task.setStatus(newStatus);

        Task updated = taskRepository.save(task);
        return taskMapper.toDTO(updated);
    }


    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task com id " + taskId + " não encontrada.");
        }
        taskRepository.deleteById(taskId);
    }

}
