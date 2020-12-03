package co.com.poli.pojecttask.domain;


import co.com.poli.pojecttask.model.ProjectTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "tbl_project_tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectTask {

    @Id
    @NotNull(message = "El Id es requerido")
    @Column(name ="id", unique = true)
    private Long id;

    @NotBlank(message = "El name es requerido")
    @Column(name = "project_name", unique = true)
    private String name;

    @NotBlank(message = "El summary es requerido")
    private String summary;

    @Column(name = "acceptence_criteria")
    private String acceptanceCriteria;

    // Validar enum
    @Enumerated(EnumType.STRING)
    private ProjectTaskStatus status;

    @Min(value = 1,message = "La prioridad debe ser de 1 a 5")
    @Max(value = 5,message = "La prioridad debe ser de 1 a 5")
    private int priority;

    @Positive(message = "Las horas deben de ser positivas")
    @Min(value = 1,message = "Las horas deben ser de 1 a 8")
    @Max(value = 8,message = "Las horas deben ser de 1 a 8")
    private Double hours;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "project_identifier",unique = true, updatable = false)
    private String projectIdentifier;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "backlog_id")
    @Transient
    private Backlog backlog;

}
