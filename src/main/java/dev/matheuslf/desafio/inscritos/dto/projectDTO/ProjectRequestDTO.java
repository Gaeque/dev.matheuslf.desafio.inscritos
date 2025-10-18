package dev.matheuslf.desafio.inscritos.dto.projectDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDTO {

    @NotBlank(message = "O nome do projeto é obrigatório")
    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
