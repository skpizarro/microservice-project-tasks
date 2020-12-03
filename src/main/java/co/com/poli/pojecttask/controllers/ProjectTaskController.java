package co.com.poli.pojecttask.controllers;

import co.com.poli.pojecttask.domain.ProjectTask;
import co.com.poli.pojecttask.model.ErrorMessage;
import co.com.poli.pojecttask.services.IProjectTaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Llave duplicada: el projectName y projectIdentifier deben ser valores únicos");
        }

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