package co.com.poli.pojecttask.services;

import co.com.poli.pojecttask.domain.ProjectTask;

import java.util.List;

public interface IProjectTaskService {

    ProjectTask createNewProjectTask(ProjectTask projectTask);

    List<ProjectTask> getAllTasksByProjectIdentifier (String projectIdentifier);

    //int getHoursProject

}
