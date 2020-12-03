package co.com.poli.pojecttask.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "tbl_backlogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El id lo genera automaticamente dependiendo del motor de base de datos
    private Long id;

    @NotBlank(message = "El project identifier es requerido")
    @Column(name = "project_identifier")
    private String projectIdentifier;

    @Transient
    @OneToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL) // el cascade es para cuando se modifica se modifica en todas las tablas , como es una relacion el fetch LAZY  es perezoso hasta que no obtenga el project no viene
    @JoinColumn(name = "project_id") // Para relacionar con la otra tabla
    private Project project;

    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "project_task_id")
    private List<ProjectTask> projectTask;

}
