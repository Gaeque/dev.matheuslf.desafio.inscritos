package dev.matheuslf.desafio.inscritos.mapper;


import dev.matheuslf.desafio.inscritos.dto.projectDTO.ProjectResponseDTO;
import dev.matheuslf.desafio.inscritos.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ProjectMapper {

    @Mapping(source = "tasks", target = "tasks")
    ProjectResponseDTO toDTO(Project project);

    List<ProjectResponseDTO> toDTOList(List<Project> projects);
}
