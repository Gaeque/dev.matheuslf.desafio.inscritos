package dev.matheuslf.desafio.inscritos.dto.taskDTO;

import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusRequestDTO {
    @NotNull(message = "Status é obrigatório")
    private StatusEnum status;
}
