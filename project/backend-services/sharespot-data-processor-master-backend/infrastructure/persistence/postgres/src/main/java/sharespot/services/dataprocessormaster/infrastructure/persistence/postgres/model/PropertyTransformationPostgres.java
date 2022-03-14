package sharespot.services.dataprocessormaster.infrastructure.persistence.postgres.model;

import javax.persistence.*;

@Entity(name = "property_transformation")
public class PropertyTransformationPostgres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long persistenceId;

    public String oldPath;

    @ManyToOne
    public DataTransformationPostgres transformation;

    @Embedded
    public PropertyNamePostgres name;
}
