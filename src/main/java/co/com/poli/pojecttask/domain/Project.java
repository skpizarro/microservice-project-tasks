package co.com.poli.pojecttask.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "tbl_projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name" , unique = true)
    @NotEmpty(message = "El project name es requerido")
    private String projectName;

    @Column(name = "project_identifier", unique = true, updatable = false)
    @NotBlank(message = "El project identifier es requerido")
    @Size(min = 5,max = 7,message = "El project identifer debe estar entre 5 y 7 caracteres")
    private String projectIdentifier;

    @Column(name = "description")
    @NotBlank(message = "La descripción es requerida")
    private String description;

    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtDate;

    @JoinColumn(name = "backlog_id") //
    @OneToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private Backlog backlog;


}
