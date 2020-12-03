package co.com.poli.pojecttask.controllers;


import co.com.poli.pojecttask.domain.ProjectTask;
import co.com.poli.pojecttask.model.ErrorMessage;
import co.com.poli.pojecttask.model.ProjectTaskStatus;
import co.com.poli.pojecttask.services.IProjectTaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/task")
public class ProjectTaskController {

    @Autowired
    private IProjectTaskService iProjectTaskService;

    @PostMapping
    public ResponseEntity<ProjectTask> createNewProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result){

        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,formatMessage(result));
        }

        try {
            ProjectTask projectTaskDB = iProjectTaskService.createNewProjectTask(projectTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(projectTaskDB);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Llave duplicada: El name debe ser un valor unico");
        }

    }

    @GetMapping(value = "/project/{projectIdentifier}")
    public ResponseEntity<List<ProjectTask>> getAllTasksByProjectIdentifier(@PathVariable("projectIdentifier") String projectIdentifier){

        List<ProjectTask> listTaskDB = iProjectTaskService.getAllTasksByProjectIdentifier(projectIdentifier);

        if(listTaskDB.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El proyecto no existe");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listTaskDB);
    }

    @GetMapping(value = "/hours/project/{projectIdentifier}")
    public ResponseEntity<Map> hoursByProject(@PathVariable("projectIdentifier") String projectIdentifier){

        List<ProjectTask> listTaskDB = iProjectTaskService.getAllTasksByProjectIdentifier(projectIdentifier);

        if(listTaskDB.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El proyecto no existe");
        }
        Map resp = iProjectTaskService.calculateHoursProject(listTaskDB,null);
        return ResponseEntity.status(HttpStatus.OK).body(resp);

    }

    @GetMapping(value = "/hours/project/{projectIdentifier}/{status}")
    public ResponseEntity<Map> hoursByProjectAndStatus(@PathVariable("projectIdentifier") String projectIdentifier,
                                                       @PathVariable("status") ProjectTaskStatus status){

        List<ProjectTask> listTaskDB = iProjectTaskService.getTasksByProjectIdentifierAndStatus(projectIdentifier, status);

        if(listTaskDB.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El proyecto con el estado ingresado no existe");
        }

        Map resp = iProjectTaskService.calculateHoursProject(listTaskDB,status);

        return ResponseEntity.status(HttpStatus.OK).body(resp);

    }

    @DeleteMapping(value = "/{idTask}/{projectIdentifier}")
    public ResponseEntity<ProjectTask> deleteProjectTask(@PathVariable("idTask") Long id,
                                                         @PathVariable("projectIdentifier") String projectIdentifier){
        ProjectTask projectTaskDB = iProjectTaskService.getProjectTasksByIdAndProjectIdentifier(id,projectIdentifier);

        if(projectTaskDB==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La tarea no existe");
        }

        ProjectTask projectTaskDeleted = iProjectTaskService.deleteProjectTask(projectTaskDB);
        return ResponseEntity.status(HttpStatus.OK).body(projectTaskDeleted);
    }


    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(),err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }

}
