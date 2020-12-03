package co.com.poli.pojecttask.services;

import co.com.poli.pojecttask.domain.ProjectTask;
import co.com.poli.pojecttask.repository.IProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService implements IProjectTaskService{

    @Autowired
    private IProjectTaskRepository iProjectTaskRepository;

    @Override
    public ProjectTask createNewProjectTask(ProjectTask projectTask) {
        return iProjectTaskRepository.save(projectTask);
    }

    @Override
    public List<ProjectTask> getAllTasksByProjectIdentifier(String projectIdentifier) {
        return iProjectTaskRepository.findProjectTasksByProjectIdentifier(projectIdentifier);
    }
}
