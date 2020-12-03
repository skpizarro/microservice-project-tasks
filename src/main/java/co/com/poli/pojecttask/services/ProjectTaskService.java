package co.com.poli.pojecttask.services;

import co.com.poli.pojecttask.domain.ProjectTask;
import co.com.poli.pojecttask.model.ProjectTaskStatus;
import co.com.poli.pojecttask.repository.IProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Map calculateHoursProject(List<ProjectTask> listTaskDB, ProjectTaskStatus status) {
        Double hoursProject=0.0;

        if(status == null){
            //Todas las tareas que tengan estado diferente a DELETED
            hoursProject = listTaskDB.stream()
                    .filter(task-> !task.getStatus().toString().equals("DELETED"))
                    .collect(Collectors.summingDouble(task->task.getHours()));
        }else{
            //Tareas con estado especifico
            hoursProject = listTaskDB.stream()
                    .filter(task-> task.getStatus().equals(status))
                    .collect(Collectors.summingDouble(task-> task.getHours()));
        }

        Map resp = new HashMap<>();
        resp.put("hoursProject",hoursProject);

        return resp;
    }

    @Override
    public List<ProjectTask> getTasksByProjectIdentifierAndStatus(String projectIdentifier, ProjectTaskStatus status) {
        return iProjectTaskRepository.findProjectTasksByProjectIdentifierAndStatus(projectIdentifier,status);
    }

    @Override
    public ProjectTask getProjectTasksByIdAndProjectIdentifier(Long id, String projectIdentifier) {
        return iProjectTaskRepository.findProjectTasksByIdAndProjectIdentifier(id,projectIdentifier);
    }

    @Override
    public ProjectTask deleteProjectTask(ProjectTask projectTask) {
        projectTask.setStatus(ProjectTaskStatus.DELETED);
        return iProjectTaskRepository.save(projectTask);
    }
}
