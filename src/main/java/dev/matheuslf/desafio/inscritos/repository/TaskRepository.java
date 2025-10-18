package dev.matheuslf.desafio.inscritos.repository;

import dev.matheuslf.desafio.inscritos.entity.Task;
import dev.matheuslf.desafio.inscritos.enums.PriorityEnum;
import dev.matheuslf.desafio.inscritos.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t " +
            "WHERE (:status IS NULL OR t.status = :status) " +
            "AND (:priority IS NULL OR t.priority = :priority) " +
            "AND (:projectId IS NULL OR t.project.id = :projectId)")
    List<Task> findByFilters(@Param("status") StatusEnum status,
                             @Param("priority") PriorityEnum priority,
                             @Param("projectId") Long projectId);
}
