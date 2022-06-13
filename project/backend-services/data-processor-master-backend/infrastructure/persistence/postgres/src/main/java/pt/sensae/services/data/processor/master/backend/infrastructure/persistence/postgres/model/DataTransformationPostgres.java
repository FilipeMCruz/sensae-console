package pt.sensae.services.data.processor.master.backend.infrastructure.persistence.postgres.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "transformation")
public class DataTransformationPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    @Column(unique = true)
    public String deviceType;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transformation", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<PropertyTransformationPostgres> entries;
}
