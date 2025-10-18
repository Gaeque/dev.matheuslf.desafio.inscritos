package dev.matheuslf.desafio.inscritos.dto.taskDTO;

import dev.matheuslf.desafio.inscritos.enums.PriorityEnum;
import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private StatusEnum status;
    private PriorityEnum priority;
    private LocalDate dueDate;
    private Long projectId;
    private String projectName;
}