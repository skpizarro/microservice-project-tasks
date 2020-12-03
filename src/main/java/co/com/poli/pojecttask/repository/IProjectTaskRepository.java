package co.com.poli.pojecttask.repository;


import co.com.poli.pojecttask.domain.Project;
import co.com.poli.pojecttask.domain.ProjectTask;
import co.com.poli.pojecttask.model.ProjectTaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectTaskRepository extends JpaRepository<ProjectTask,Long> {

    List<ProjectTask> findProjectTasksByProjectIdentifier(String projectIdenfifier);
    List<ProjectTask> findProjectTasksByProjectIdentifierAndStatus(String projectIdenfifier, ProjectTaskStatus status);
    ProjectTask findProjectTasksByIdAndProjectIdentifier(Long id, String projectIdentifier);
}
