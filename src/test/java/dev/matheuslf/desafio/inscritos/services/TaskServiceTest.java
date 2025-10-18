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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void deveCriarTarefaComSucesso() {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Nova Tarefa");
        requestDTO.setDescription("Descrição da tarefa");
        requestDTO.setStatus(StatusEnum.TODO);
        requestDTO.setPriority(PriorityEnum.HIGH);
        requestDTO.setDueDate(LocalDate.now().plusDays(7));
        requestDTO.setProjectId(1L);

        Project project = new Project();
        project.setId(1L);
        project.setName("Projeto Teste");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Nova Tarefa");
        task.setProject(project);

        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("Nova Tarefa");
        responseDTO.setProjectId(1L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(responseDTO);

        TaskResponseDTO result = taskService.saveTask(requestDTO);

        assertNotNull(result);
        assertEquals("Nova Tarefa", result.getTitle());
        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deveLancarExcecaoQuandoProjetoNaoExiste() {
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Tarefa");
        requestDTO.setProjectId(999L);

        when(projectRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            taskService.saveTask(requestDTO);
        });

        assertTrue(exception.getMessage().contains("Projeto não encontrado"));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void deveAtualizarStatusDaTarefa() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Tarefa");
        task.setStatus(StatusEnum.TODO);

        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setStatus(StatusEnum.DOING);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(responseDTO);

        TaskResponseDTO result = taskService.updateTask(1L, StatusEnum.DOING);

        assertNotNull(result);
        assertEquals(StatusEnum.DOING, result.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deveDeletarTarefaComSucesso() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}