package co.com.poli.pojecttask.services;

import co.com.poli.pojecttask.domain.ProjectTask;
import co.com.poli.pojecttask.model.ProjectTaskStatus;


import java.util.List;
import java.util.Map;

public interface IProjectTaskService {

    ProjectTask createNewProjectTask(ProjectTask projectTask);

    List<ProjectTask> getAllTasksByProjectIdentifier (String projectIdentifier);

    Map calculateHoursProject(List<ProjectTask> listTaskDB, ProjectTaskStatus status);

    List<ProjectTask> getTasksByProjectIdentifierAndStatus(String projectIdentifier, ProjectTaskStatus status);

    ProjectTask getProjectTasksByIdAndProjectIdentifier(Long id, String projectIdentifier);

    ProjectTask deleteProjectTask(ProjectTask projectTask);


}
