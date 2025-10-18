package dev.matheuslf.desafio.inscritos.dto.taskDTO;

import dev.matheuslf.desafio.inscritos.enums.PriorityEnum;
import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "O título da tarefa é obrigatório")
    private String title;

    private String description;

    private StatusEnum status;

    private PriorityEnum priority;

    private LocalDate dueDate;

    @NotNull(message = "O projeto associado é obrigatório")
    private Long projectId;
}